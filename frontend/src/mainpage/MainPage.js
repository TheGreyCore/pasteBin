import React, { useState } from 'react';
import {Alert, AlertLink, Button, Dropdown, DropdownButton} from "react-bootstrap";
import ButtonGroup from "react-bootstrap/ButtonGroup";
import './MainPage.css';
import {useParams} from "react-router-dom";
import NavbarComponent from "../navbar/Navbar";
import FooterComponent from "../footer/Footer";

const MainPage = () => {
    let backEndURL = "http://localhost:8080";
    const [fileContent, setFileContent] = useState("");
    const files = [
        {Filename: "Example bin 1.", fileContent: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},
        {Filename: "Example bin 2.", fileContent: "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure."},
    ];

    const handleSelect = (key) => {
        setFileContent(files[key].fileContent);
    };

    async function handleLogin() {
        window.location.href = backEndURL + "/oauth2/authorization/google";
    }

    return(
        <>
            <NavbarComponent></NavbarComponent>
            <div className="content-box">

                <div className="label-box">
                    <h1 className="">Welcome to the simple pasteBin!</h1>
                    <br></br>
                    <h5>Bellow is an example of an actual web page for those who don't want to log in to test the
                        application.</h5>
                    <hr></hr>
                    <Alert variant="warning" onClick={handleLogin}>If you want to test application in live,
                        please <AlertLink>log in</AlertLink> using Google oauth2. </Alert>
                    <hr></hr>
                </div>
                <div className="edit-box">
                    <textarea
                        className="form-control"
                        id="content-textarea"
                        rows="15"
                        value={fileContent}
                        onChange={e => setFileContent(e.target.value)}
                    />
                    <div className="tool-box">
                        <ButtonGroup aria-label="Basic example">
                            <Button variant="secondary">Save</Button>
                            <Button variant="secondary">Delete</Button>
                            <Button id="share-button" variant="success">Share</Button>
                            <DropdownButton as={ButtonGroup} title="Saved documents" id="bg-nested-dropdown"
                                            variant="secondary" onSelect={handleSelect}>
                                {files.map((file, index) => (
                                    <Dropdown.Item onSelect={handleSelect}
                                                   eventKey={index}>{file.Filename}</Dropdown.Item>
                                ))}
                            </DropdownButton>
                        </ButtonGroup>
                    </div>

                </div>

                <Alert variant="info">We use oauth2 login only to limit application load. This site has been created for
                    demonstration purposes only. Read more about project...</Alert>
            </div>
            <FooterComponent></FooterComponent>
        </>
    )
}

export default MainPage;
