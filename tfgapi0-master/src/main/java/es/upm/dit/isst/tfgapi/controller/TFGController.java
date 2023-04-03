package es.upm.dit.isst.tfgapi.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.upm.dit.isst.tfgapi.model.TFG;
import es.upm.dit.isst.tfgapi.repository.TFGRepository;

@RestController
public class TFGController {
    private final TFGRepository tfgRepository;
    public static final Logger log = LoggerFactory.getLogger(TFGController.class);
    public TFGController(TFGRepository t) {
        this.tfgRepository = t;
    }
    @GetMapping("/tfgs")
    List<TFG> readAll() {
      return (List<TFG>) tfgRepository.findAll();
    }
 
    @PostMapping("/tfgs") // no detecta repetidos, neceista un TFG en el cuerpo del método
    ResponseEntity<TFG> create(@RequestBody TFG newTFG) throws URISyntaxException {
      TFG result = tfgRepository.save(newTFG); // voy al repositorio con el nuevo TFG
      return ResponseEntity.created(new URI("/tfgs/" + result.getEmail())).body(result); // mando una respuesta diciendo que se ha creado el objeto
    }
 
    // devuelve el resultado de ir al repositorio de TFGs y buscar el TFG concreto, si no lo encuentra devuelve
    // dentro de la función map tenemos un parámetro tfg y con él vamos a hacer algo
    // no hay que poner return ni llaves porque está implicito pero el nombre del parámetro hay que ponerlo porque lo utilizamos
    @GetMapping("/tfgs/{id}") 
    ResponseEntity<TFG> read(@PathVariable String id) {
      return tfgRepository.findById(id).map(tfg ->
         ResponseEntity.ok().body(tfg)
      ).orElse(new ResponseEntity<TFG>(HttpStatus.NOT_FOUND));
    }

    // como tenemos muchas sentencias hay que poner un return y llaves
    // Lo encuentro, le cambio los atributos, lo guardo y lo devuelvo
    @PutMapping("/tfgs/{id}")
    ResponseEntity<TFG> update(@RequestBody TFG newTFG, @PathVariable String id) {
      return tfgRepository.findById(id).map(tfg -> {
        tfg.setNombre(newTFG.getNombre());
        tfg.setTitulo(newTFG.getTitulo());
        tfg.setTutor(newTFG.getTutor());
        tfg.setStatus(newTFG.getStatus());
        tfg.setNota(newTFG.getNota());
        tfg.setMemoria(newTFG.getMemoria());
        tfgRepository.save(tfg);
        return ResponseEntity.ok().body(tfg);
      }).orElse(new ResponseEntity<TFG>(HttpStatus.NOT_FOUND));
    }

    // todas las operaciones delete devuelven un ok
    @DeleteMapping("tfgs/{id}")
    ResponseEntity<TFG> delete(@PathVariable String id) {
      tfgRepository.deleteById(id);
      return ResponseEntity.ok().body(null);
    }
    // metodo para leer todos los TFGs que tiene un tutor
    //@PathVarible toma el valor de la url y lo toma como parámetro
    @GetMapping("/tfgs/profesor/{id}")
    List<TFG> readTutor(@PathVariable String id) {
      return (List<TFG>) tfgRepository.findByTutor(id);
    }

    // incrementa en 1 el status del TFG
    // si encuentro el TFG cojo su status +1, lo guardo y lo devuelvo
    @PostMapping("/tfgs/{id}/incrementa")
    ResponseEntity<TFG> incrementa(@PathVariable String id) {
      return tfgRepository.findById(id).map(tfg -> {
        tfg.setStatus(tfg.getStatus() + 1);
        tfgRepository.save(tfg);
        return ResponseEntity.ok().body(tfg);
      }).orElse(new ResponseEntity<TFG>(HttpStatus.NOT_FOUND));  
    }
}
