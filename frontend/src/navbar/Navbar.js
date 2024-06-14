import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import {Button} from "react-bootstrap";
import React from "react";

const handleLogout = async () => {
    await fetch(
        'http://localhost:8080/logout',
        { method: 'POST', redirect: "follow", credentials: 'include'})
    window.location.href = "/";
}

function NavbarComponent({name, family_name}) {
    return (
        <Navbar className="bg-body-tertiary">
            <Container>
                <Navbar.Brand href="/profile">Easy pasteBin</Navbar.Brand>
                <Navbar.Toggle />
                <Navbar.Collapse className="justify-content-end">
                    <Navbar.Text>
                        Signed in as: <a href="/profile">{name + " " + family_name}</a>
                    </Navbar.Text>
                </Navbar.Collapse>
                <Button variant="secondary" onClick={handleLogout}>Log out</Button>
            </Container>
        </Navbar>
    );
}

export default NavbarComponent;