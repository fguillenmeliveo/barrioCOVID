import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';

class TFGEdit extends Component {

    emptyItem = {
        nombre: '',
        email: '',
        titulo: '',
        tutor: '',
        pass: '',
        status: 1,
        memoria: null,
        nota: 0
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.email !== 'new') { // 
            const tfg = await (await fetch(`/tfgs/${this.props.match.params.email}`)).json();
            this.setState({item: tfg});
        }
    }

    handleChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

async handleSubmit(event) {
    event.preventDefault();
    const {item} = this.state;

    await fetch('/tfgs' + ((this.props.match.params.email !== 'new') ? '/' + item.email : ''), {
        method: (this.props.match.params.email !== 'new') ? 'PUT' : 'POST', //(item.email) ? 'PUT' : 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(item),
    });
    this.props.history.push('/tfgs');
}

    render() {
        const {item} = this.state;
        const title = <h2>{item.email ? 'Edit TFG' : 'Add TFG'}</h2>;

        return <div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={this.handleSubmit}>
                    <FormGroup>
                        <Label for="nombre">Nombre</Label>
                        <Input type="text" name="nombre" id="nombre" value={item.nombre || ''}
                               onChange={this.handleChange} autoComplete="nombre"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="email">Email</Label>
                        <Input type="text" name="email" id="email" value={item.email || ''}
                               onChange={this.handleChange} autoComplete="email"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="titulo">Titulo</Label>
                        <Input type="text" name="titulo" id="titulo" value={item.titulo || ''}
                               onChange={this.handleChange} autoComplete="titulo"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="tutor">Tutor</Label>
                        <Input type="text" name="tutor" id="tutor" value={item.tutor || ''}
                               onChange={this.handleChange} autoComplete="tutor"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="status">Status</Label>
                        <Input type="number" name="status" id="status" value={item.status || ''}
                               onChange={this.handleChange} autoComplete="status"/>
                    </FormGroup>

                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/tfgs">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    }
}

export default withRouter(TFGEdit);