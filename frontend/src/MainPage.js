import React from "react";
import {Alert, AlertLink, Button, Dropdown, DropdownButton} from "react-bootstrap";
import ButtonGroup from "react-bootstrap/ButtonGroup";

const MainPage = () => {
    async function handleLogin() {
        window.location.href = "http://localhost:8080/oauth2/authorization/github";
    }

    return(
        <div>
            <div className="content-box">
                <div className="label-box">
                    <h1 className="">Welcome to simple pasteBin!</h1>
                    <h2>This is an example of an actual web page for those who don't want to log in to test the
                        application.</h2>
                </div>

                <Alert variant="warning" onClick={handleLogin}>If you want to test application in live,
                    please <AlertLink>log in</AlertLink> using GitHub oauth2. </Alert>
                <Alert variant="info">We use oauth2 login only to limit application load. This site has been created for demonstration purposes only. </Alert>
                <h6>File name:</h6>
                <textarea readOnly className="form-control" rows="1"></textarea>
                <h6>File content:</h6>
                <textarea readOnly className="form-control" rows="5"></textarea>
                <ButtonGroup aria-label="Basic example">
                    <Button variant="secondary">Save</Button>
                    <Button variant="secondary">Delete</Button>
                    <DropdownButton as={ButtonGroup} title="Saved documents" id="bg-nested-dropdown" variant="secondary">
                        {/*TODO: Get all saved bins and make dropdown link for them*/}
                        <Dropdown.Item eventKey="1">Dropdown link</Dropdown.Item>
                        <Dropdown.Item eventKey="2">Dropdown link</Dropdown.Item>
                    </DropdownButton>
                    <Button variant="secondary">Log out</Button>
                </ButtonGroup>

            </div>
        </div>
    )
}

export default MainPage;
