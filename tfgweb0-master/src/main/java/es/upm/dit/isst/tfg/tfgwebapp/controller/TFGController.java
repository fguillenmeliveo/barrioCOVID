package es.upm.dit.isst.tfg.tfgwebapp.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import es.upm.dit.isst.tfg.tfgwebapp.model.TFG;

@Controller
public class TFGController {
        public final String TFGMANAGER_STRING= "http://localhost:8083/tfgs/";
        public static final String VISTA_LISTA = "lista";
        public static final String VISTA_FORMULARIO = "formulario";
        private RestTemplate restTemplate = new RestTemplate(); //objeto auxiliar que nos ayuda a hacer las peticiones contra el servicio de persistencia

        @GetMapping("/")
        public String inicio() {
                return "redirect:/" + VISTA_LISTA;
        }
        @GetMapping("/login")
        public String login() {
                return "redirect:/" + VISTA_LISTA;
        }

        //Objeto Principal contiene los objetos de autenticación que haya puesto el usuario en la sesión
        //Este método tomas los paárametros de la propia petición HTTP
       @GetMapping("/lista") 
        public String lista(Model model, Principal principal) {
                List<TFG> lista = new ArrayList<TFG>();
                if (principal == null || principal.getName().equals(""))
                        lista = Arrays.asList(restTemplate.getForEntity(TFGMANAGER_STRING, 
                                TFG[].class).getBody()); // con esta ´linea le decimos que convierta el JSON en un array
                else if (principal.getName().contains("@upm.es"))
                        lista = Arrays.asList(restTemplate.getForEntity(TFGMANAGER_STRING + "profesor/" + principal.getName()
                                 ,TFG[].class).getBody());
                else if (principal.getName().contains("@alumnos.upm.es")){
                        try { TFG tfg = restTemplate.getForObject(TFGMANAGER_STRING
                                    + principal.getName(), TFG.class);
                          if (tfg != null)
                                lista.add(tfg);
                        } catch (Exception e) {}
                }
                model.addAttribute("tfgs", lista);
                return VISTA_LISTA;
        }
        @GetMapping("/crear")
        public String crear(Map<String, Object> model) {
                TFG TFG = new TFG();
                model.put("TFG", TFG);
                model.put("accion", "guardar");
                return VISTA_FORMULARIO;
        }

        // El servicio va a leer el cuerpo del método post, lo va a convertir en un TFG, va a realizar las comprobaciones de nombre, email y titulo nos va a poner el objeto
        // en result nos va a poner los errores que haya habido, si hay algún error nos devuelve el formulario
        @PostMapping("/guardar")
        public String guardar(@Validated TFG TFG, BindingResult result) {
                if (result.hasErrors()) {
                        return VISTA_FORMULARIO;
                } // para crear un tfg usamos el método post
                try { restTemplate.postForObject(TFGMANAGER_STRING, TFG, TFG.class); // llamamo la servicio template (persistencia) para guardar un TFG
                } catch(Exception e) {}
                return "redirect:" + VISTA_LISTA;
        }

        @GetMapping("/editar/{id}") // hay que comprobar que el principal es el correcto
        public String editar(@PathVariable(value = "id") String id,
                   Map<String, Object> model, Principal principal) {
                if (principal == null || ! principal.getName().equals(id))
                        return "redirect:/" + VISTA_LISTA;
                TFG tfg = null;
                try { tfg = restTemplate.getForObject(TFGMANAGER_STRING + id, TFG.class);
                } catch (HttpClientErrorException.NotFound ex) {}
                model.put("TFG", tfg); //lo pongo en la tabla modelo
                model.put("accion", "../actualizar"); //hago las acciones correspondientes sobre el TFG
                return tfg != null ? VISTA_FORMULARIO : "redirect:/" + VISTA_LISTA;
        }
        @PostMapping("/actualizar")
        public String actualizar(@Validated TFG tfg, BindingResult result) {
                if (result.hasErrors()) {
                        return VISTA_FORMULARIO;
                }
                try { restTemplate.put(TFGMANAGER_STRING + tfg.getEmail(), tfg, TFG.class); // para actualizar un tfg, utilizamos el método put
                } catch(Exception e) {}
                return "redirect:" + VISTA_LISTA;
        }

        @GetMapping("/eliminar/{id}")
        public String eliminar(@PathVariable(value = "id") String id) {
                restTemplate.delete(TFGMANAGER_STRING+ id);
                return "redirect:/" + VISTA_LISTA;
        }
       @GetMapping("/aceptar/{id}")
       public String aceptar(@PathVariable(value = "id") String id,
                       Map<String, Object> model, Principal principal) {
                if (principal != null) {
                        try { TFG tfg = restTemplate.getForObject(TFGMANAGER_STRING + id,
                                  TFG.class);
                                if (tfg != null
                                  && principal.getName().equals(tfg.getTutor())) {
                               restTemplate.postForObject(TFGMANAGER_STRING
                                  +tfg.getEmail()+"/incrementa", tfg, TFG.class);
                                  model.put("TFG", tfg);
                                }
                        } catch (HttpClientErrorException.NotFound ex) { }
                }
                return "redirect:/" + VISTA_LISTA;
        }

        @PostMapping("/upload")
     public String uploadFile(@RequestParam("file") MultipartFile file,
                     @RequestParam("email") String email, Principal principal) {
        if (principal == null || principal.getName() == null ||
                  ! principal.getName().equals(email) || file.isEmpty())
          return "redirect:/" + VISTA_LISTA;
        try { TFG tfg = restTemplate.getForObject(TFGMANAGER_STRING + email, TFG.class);
                if (tfg != null && tfg.getStatus() == 3) {
                        tfg.setStatus(tfg.getStatus() + 1);
                        tfg.setMemoria(file.getBytes());
                        restTemplate.put(TFGMANAGER_STRING + tfg.getEmail(),
                                tfg,TFG.class);
                }
        } catch (Exception e) {}
       return "redirect:/" + VISTA_LISTA;
    }
    @GetMapping("/download/{email}")
    @ResponseBody
    public ResponseEntity<ByteArrayResource> getFile(@PathVariable String email) {
        try { TFG tfg = restTemplate.getForObject(TFGMANAGER_STRING + email, TFG.class);
                if (tfg != null && tfg.getMemoria() != null) {
                    HttpHeaders header = new HttpHeaders();
                        header.setContentType(new MediaType("application", "force-download"));
                        header.set(HttpHeaders.CONTENT_DISPOSITION,
                         "attachment; filename=\"TFG.pdf\"");
                        ByteArrayResource resource = new ByteArrayResource(tfg.getMemoria());
                        return new ResponseEntity<ByteArrayResource>(resource,
                         header, HttpStatus.CREATED);
                }
        } catch (Exception e) {}
        return new ResponseEntity<ByteArrayResource>(HttpStatus.NOT_FOUND);
   }
}
