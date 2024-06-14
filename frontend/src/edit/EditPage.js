import React, { useEffect, useState } from 'react';
import ButtonGroup from "react-bootstrap/ButtonGroup";
import {Button, Dropdown, DropdownButton} from "react-bootstrap";
import {useParams} from "react-router-dom";
import Nav from "react-bootstrap/Nav";
import NavbarComponent from "../navbar/Navbar";

const EditPage = () => {
    let params = useParams();
    const [sharedButtonClicked, setSharedButtonClicked] = useState(false);
    const [userData, setUserData] = useState(null);
    const [fileContent, setFileContent] = useState(null);

    useEffect(() => {
        fetch(
            'http://localhost:8080/api/user',
            {method: 'GET', credentials: 'include'}
        ).then((res) => { return res.json();})
            .then((responseData) => {
            setUserData(responseData)})
            .catch(error => handleErrors(error));
        fetch(
            "http://localhost:8080/api/getBinByURL?url=" + params.binURL,
            {method: 'GET'}
        ).then((res) => { return res.json();})
            .then((responseData) => {
                setFileContent(responseData.body)})
            .catch(error => handleErrors(error));
    }, [params.binURL]);

    const handleErrors = (error) => {
        console.error('Error:', error);
        window.location.href = "/error";
    }

    const handleLogout = async () => {
        await fetch(
            'http://localhost:8080/logout',
            { method: 'POST', redirect: "follow", credentials: 'include'})
            setUserData(null);
            window.location.href = "/";
    }

    const handleSave = async () => {
        const url = 'http://localhost:8080/api/updateBlob';
        const data = {
            body: fileContent,
            fileName: params.binURL,
        };

        try {
            const response = await fetch(url, {
                method: 'POST',
                credentials: 'include',
                redirect: 'follow',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(data),
            });

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const responseData = await response.json();
            console.log(responseData)
        } catch (error) {
            console.error('Error:', error);
        }
    }

    const handleDelete = async () => {
        const confirmDelete = window.confirm('Are you sure you want to delete this bin?');
        if (confirmDelete) {
            await fetch(
                "http://localhost:8080/api/deleteBin?url=" + params.binURL,
                {method: 'DELETE', credentials: 'include'}
            ).then((res) => {
                window.location.href = "/profile"
            });
        }
    }

    function handleShare() {
        setSharedButtonClicked(!sharedButtonClicked);
        let link = "localhost:3000/share/" + params.binURL;
        navigator.clipboard.writeText(link).then().catch(ignore => {
            window.location.href = link
        });

        document.getElementById("share-button").innerText = "Link copied!"
    }

    return (
        <div>
            <NavbarComponent name={userData ? userData.first_name : "Unknown"} family_name={userData ? userData.family_name : "Unknown"}></NavbarComponent>
            <div className="content-box">
                <h1 className="">Welcome, {userData ? userData.first_name : <>Unknown</>}!</h1>
                <hr></hr>
                <h6>File content:</h6>
                <textarea
                    className="form-control"
                    id="content-textarea"
                    rows="5"
                    value={fileContent}
                    onChange={e => setFileContent(e.target.value)}
                />
                <ButtonGroup aria-label="Basic example">
                    <Button variant="secondary" onClick={handleSave}>Save</Button>
                    <Button variant="secondary" onClick={handleDelete}>Delete</Button>
                    <Button id="share-button" variant="success" onClick={handleShare}>Share</Button>
                    <DropdownButton as={ButtonGroup} title="Saved documents" id="bg-nested-dropdown"
                                    variant="secondary">
                        {/*TODO: Get all saved bins and make dropdown link for them*/}
                        <Dropdown.Item eventKey="1">Dropdown link</Dropdown.Item>
                        <Dropdown.Item eventKey="2">Dropdown link</Dropdown.Item>
                    </DropdownButton>
                </ButtonGroup>
            </div>
            <br/>
        </div>
    );
}

export default EditPage;
