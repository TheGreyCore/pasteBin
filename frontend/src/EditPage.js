import React, { useEffect, useState } from 'react';
import ButtonGroup from "react-bootstrap/ButtonGroup";
import {Button, Dropdown, DropdownButton} from "react-bootstrap";

const EditPage = () => {
    const [data, setData] = useState(null);
    const [fileName, setFileName] = useState(null);
    const [fileContent, setFileContent] = useState(null);

    useEffect(() => {
        fetch(
            'http://localhost:8080/user',
            {method: 'GET', credentials: 'include'}
        ).then((res) => { return res.json();})
            .then((responseData) => {
            setData(responseData)})
            .catch(error => handleErrors(error));
    }, []);

    const handleErrors = (error) => {
        console.error('Error:', error);
        window.location.href = "/error";
    }

    const handleLogout = async () => {
        await fetch(
            'http://localhost:8080/logout',
            { method: 'POST', redirect: "follow", credentials: 'include'})
            setData(null);
            window.location.href = "/";
    }

    const handleSave = async () => {
        setFileName(document.getElementById("filename-textarea").value);
        setFileContent(document.getElementById("content-textarea").value);
        // Just for debbuging TODO: Replace
        console.log(fileName);
        console.log(fileContent);
        // TODO: await fetch to backend to save bin
    }

    const handleDelete = async () => {
        document.getElementById("filename-textarea").value = '';
        document.getElementById("content-textarea").value = '';
        setFileName(null);
        setFileContent(null);

        // Just for debbuging TODO: Replace
        console.log(fileName);
        console.log(fileContent);
        // TODO: await fetch to backend to delete bin
    }

    return (
        <div>
            <div className="content-box">
                <h1 className="">Welcome, {data ? data.name: <>Unknown</>}!</h1>
                <h6>File name:</h6>
                <textarea className="form-control" id="filename-textarea" rows="1" ></textarea>
                <h6>File content:</h6>
                <textarea className="form-control" id="content-textarea" rows="5"></textarea>
                <ButtonGroup aria-label="Basic example">
                    <Button variant="secondary" onClick={handleSave}>Save</Button>
                    <Button variant="secondary" onClick={handleDelete}>Delete</Button>
                    <DropdownButton as={ButtonGroup} title="Saved documents" id="bg-nested-dropdown" variant="secondary">
                        {/*TODO: Get all saved bins and make dropdown link for them*/}
                        <Dropdown.Item eventKey="1">Dropdown link</Dropdown.Item>
                        <Dropdown.Item eventKey="2">Dropdown link</Dropdown.Item>
                    </DropdownButton>
                    <Button variant="secondary" onClick={handleLogout}>Log out</Button>
                </ButtonGroup>
            </div>
            <br/>
        </div>
    );
}

export default EditPage;
