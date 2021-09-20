import React, {Component} from 'react';
import './css/App.css';
import AppNavbar from './AppNavbar';
import {Link} from 'react-router-dom';
import {Button, Container} from 'reactstrap';

class Home extends Component {
    render() {
        return (
            <div>
                <AppNavbar/>
                <Container fluid>
                    <Button color="link"><Link to="/books?filter=unprocessed">Unprocessed books</Link></Button>
                    <Button color="link"><Link to="/books?filter=selected">Selected books</Link></Button>
                    <Button color="link"><Link to="/books?filter=skipped">Skipped books</Link></Button>
                    <Button color="link"><Link to="/books?filter=all">All books</Link></Button>
                    <Button color="link"><Link to="/books">All books (not paged)</Link></Button>
                </Container>
            </div>
        );
    }
}

export default Home