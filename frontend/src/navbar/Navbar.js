import Container from 'react-bootstrap/Container';
import Navbar from 'react-bootstrap/Navbar';
import {Button} from "react-bootstrap";
import React, {useEffect, useState} from "react";
import './navbar.css'

function NavbarComponent() {
    let backEndURL = "http://localhost:8080";
    const [userData, setUserData] = useState(null);

    useEffect(() => {
        fetch(
            backEndURL + '/api/user',
            {method: 'GET', credentials: 'include'}
        ).then((res) => { return res.json();})
            .then((responseData) => {
                setUserData(responseData)})
            .catch(error => console.log(error));
    }, [backEndURL]);

    const handleLogout = async () => {
        await fetch(
            backEndURL + '/logout',
            { method: 'POST', redirect: "follow", credentials: 'include'})
        window.location.href = "/";
    }

    async function handleLogin() {
        window.location.href = backEndURL + "/oauth2/authorization/google";
    }

    return (
        <div className="navbar-div">
        <Navbar className="navbar w-auto">
            <Container>
                <Navbar.Brand href={userData ? "/profile" : "/"}>Simple pasteBin</Navbar.Brand>
                <Navbar.Toggle />
                <Navbar.Collapse className="justify-content-end">
                    <Navbar.Text>
                        {userData ? <>
                            Signed in as: <a href="/profile">{userData.first_name + " " + userData.family_name}</a>
                            <Button variant="outline-secondary" onClick={handleLogout}>Log out</Button>
                        </> : <>
                            <Button variant="outline-secondary" onClick={handleLogin}>Log in</Button>
                        </>}
                    </Navbar.Text>
                </Navbar.Collapse>
            </Container>
        </Navbar>
        </div>
    );
}

export default NavbarComponent;