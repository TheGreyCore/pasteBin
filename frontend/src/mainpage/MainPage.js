import React, { useState } from 'react';
import {Alert, AlertLink, Button, Dropdown, DropdownButton} from "react-bootstrap";
import ButtonGroup from "react-bootstrap/ButtonGroup";
import './MainPage.css';
import {useParams} from "react-router-dom";

const MainPage = () => {
    const { params } = useParams();
    const [fileContent, setFileContent] = useState("");
    const files = [
        {Filename: "Example bin 1.", fileContent: "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."},
        {Filename: "Example bin 2.", fileContent: "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure."},
    ];

    const handleSelect = (key) => {
        setFileContent(files[key].fileContent);
        console.log(fileContent)
    };

    async function handleLogin() {
        window.location.href = "http://localhost:8080/oauth2/authorization/github";
    }

    return(
        <div>
            <div className="content-box">

                <div className="label-box">
                    <h1 className="">Welcome to the simple pasteBin!</h1>
                    <br></br>
                    <h5>This is an example of an actual web page for those who don't want to log in to test the
                        application.</h5>
                    <hr></hr>
                </div>
                <div className="edit-box">
                    <Alert variant="warning" onClick={handleLogin}>If you want to test application in live,
                        please <AlertLink>log in</AlertLink> using GitHub oauth2. </Alert>
                    <h6>Your pasteBin content:</h6>
                    <textarea id="file-content-textarea" className="form-control" rows="7">{fileContent}</textarea>
                    <div className="tool-box">
                        <ButtonGroup aria-label="Basic example">
                            <Button variant="secondary">Save</Button>
                            <Button variant="secondary">Delete</Button>
                            <DropdownButton as={ButtonGroup} title="Saved documents" id="bg-nested-dropdown"
                                            variant="secondary" onSelect={handleSelect}>
                                {files.map((file, index) => (
                                    <Dropdown.Item onSelect={handleSelect} eventKey={index}>{file.Filename}</Dropdown.Item>
                                ))}
                            </DropdownButton>
                            <Button variant="secondary">Log out</Button>
                        </ButtonGroup>
                    </div>

                </div>

                <Alert variant="info">We use oauth2 login only to limit application load. This site has been created for demonstration purposes only. Read more about project...</Alert>
            </div>
        </div>
    )
}

export default MainPage;
