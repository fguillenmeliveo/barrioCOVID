import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

class TFGList extends Component {

    constructor(props) {
        super(props);
        this.state = {tfgs: []};
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch('/tfgs')
            .then(response => response.json())
            .then(data => this.setState({tfgs: data}));
    }

    async remove(email) {
        await fetch(`/tfgs/${email}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedTFGs = [...this.state.tfgs].filter(i => i.email !== email);
            this.setState({tfgs: updatedTFGs});
        });
    }

    render() {
        const {tfgs} = this.state;

        const tfgList = tfgs.map(tfg => {
            return <tr key={tfg.email}>
                <td style={{whiteSpace: 'nowrap'}}>{tfg.nombre}</td>
                <td>{tfg.email}</td>
                <td>{tfg.titulo}</td>
                <td>{tfg.tutor}</td>
                <td>{tfg.status}</td>
                <td>
                    <ButtonGroup>
                        <Button size="sm" color="primary" tag={Link} to={"/tfgs/" + tfg.email}>Edit</Button>
                        <Button size="sm" color="danger" onClick={() => this.remove(tfg.email)}>Delete</Button>
                    </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <div className="float-right">
                        <Button color="success" tag={Link} to="/tfgs/new">Add TFG</Button>
                    </div>
                    <h3>TFGs</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="15%">Nombre</th>
                            <th width="15%">Email</th>
                            <th width="15%">Titulo</th>
                            <th width="15%">Tutor</th>
                            <th width="10%">Estado</th>
                            <th width="30%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {tfgList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}

export default TFGList;