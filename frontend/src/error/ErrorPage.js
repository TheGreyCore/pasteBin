import React from "react";
import {Button} from "react-bootstrap";
import {useParams} from "react-router-dom";
import ButtonGroup from "react-bootstrap/ButtonGroup";
import NavbarComponent from "../navbar/Navbar";
import FooterComponent from "../footer/Footer";

const ErrorPage = () => {
    let { errorCode, errorDescription } = useParams();


    return (
        <>
            <NavbarComponent></NavbarComponent>
            <h1>Error occurred!</h1>
            <hr></hr>
            <h2>Code: {errorCode}</h2>
            <h3>Description: {atob(errorDescription)}</h3>
            <hr></hr>
            <ButtonGroup aria-label="Basic example">
                <Button href="/" variant="success">Back to welcome page.</Button>
                <Button href="/profile" variant="success">Back to profile page.</Button>
            </ButtonGroup>
            <FooterComponent></FooterComponent>
        </>
    )
}

export default ErrorPage;