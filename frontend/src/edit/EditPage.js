import React, {useEffect, useState} from 'react';
import ButtonGroup from "react-bootstrap/ButtonGroup";
import {Alert, Button, Dropdown, DropdownButton} from "react-bootstrap";
import {useParams} from "react-router-dom";
import NavbarComponent from "../navbar/Navbar";
import "../static/css/main.css"
import FooterComponent from "../footer/Footer";


const EditPage = () => {
    let backEndURL = "http://localhost:8080"
    let frontEndURL = "http://loclahost:3000"
    let params = useParams();
    const [sharedButtonClicked, setSharedButtonClicked] = useState(false);
    const [userData, setUserData] = useState(null);
    const [fileContent, setFileContent] = useState(null);

    useEffect(() => {
        fetch(
            backEndURL + '/api/user',
            {method: 'GET', credentials: 'include'}
        ).then((res) => { return res.json();})
            .then((responseData) => {
            setUserData(responseData)})
            .catch(error => handleErrors(error));
        fetch(
            backEndURL + "/api/getPasteByURL?url=" + params.binURL,
            {method: 'GET', redirect:'manual'}
        ).then((res) => { return res.json();})
            .then((responseData) => {
                setFileContent(responseData.body)})
            .catch(error => handleErrors(error));
    }, [backEndURL, params.binURL]);

    const handleErrors = (error) => {
        window.location.href= `/error/400/${btoa(error)}`
    }

    const handleSave = async () => {
        const url = backEndURL + '/api/updatePaste';
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

            const responseData = await response.json();

            if (!response.ok) {
                window.location.href= `/error/${response.status}/${btoa(responseData.body["errorMessage"])}`
            }

        } catch (error) {
            console.error('Error:', error);
        }
    }

    const handleDelete = async () => {
        const confirmDelete = window.confirm('Are you sure you want to delete this bin?');
        if (confirmDelete) {
            await fetch(
                backEndURL + "/api/deletePaste?url=" + params.binURL,
                {method: 'DELETE', credentials: 'include'}
            ).then((res) => {
                window.location.href = "/profile"
            });
        }
    }

    function handleShare() {
        setSharedButtonClicked(!sharedButtonClicked);
        let link = frontEndURL + "/share/" + params.binURL;
        navigator.clipboard.writeText(link).then().catch(ignore => {
            window.location.href = link
        });

        document.getElementById("share-button").innerText = "Link copied!"
    }

    const handleSelect = (url) => {
        window.location.href = "/edit/" + url
    }

    return (
        <>
            <NavbarComponent></NavbarComponent>
            <div className="content-box">
                <textarea
                    className="form-control"
                    id="content-textarea"
                    rows="15"
                    value={fileContent}
                    onChange={e => setFileContent(e.target.value)}
                />
                <ButtonGroup aria-label="Basic example">
                    <Button variant="secondary" onClick={handleSave}>Save</Button>
                    <Button variant="secondary" onClick={handleDelete}>Delete</Button>
                    <Button id="share-button" variant="success" onClick={handleShare}>Share</Button>
                    <DropdownButton as={ButtonGroup} title="Saved paste" id="bg-nested-dropdown"
                                    variant="secondary">
                        {userData ? userData.binNames.map((url, index) => (
                            <Dropdown.Item eventKey={index} onClick={() => handleSelect(url)}>PasteBin number {index + 1}</Dropdown.Item>
                        )) : <></>
                        }
                    </DropdownButton>
                </ButtonGroup>
            </div>
            <br/>
            <Alert variant="danger">Please, do not save/share any sensitive/private information (passwords,
                secrets, keys e.t.c) and remember that all pasteBins are public!</Alert>
            <FooterComponent></FooterComponent>
        </>
    );
}

export default EditPage;
