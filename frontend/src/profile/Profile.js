import React, {useEffect, useState} from 'react';
import {Button, Card, Dropdown} from "react-bootstrap";
import URLCards from "./urlcard/URLCards";
import "../static/css/main.css"
import "./Profile.css"
import NavbarComponent from "../navbar/Navbar";
import FooterComponent from "../footer/Footer";

const Profile = () => {
    let backEndURL = "http://localhost:8080";
    const [userData, setUserData] = useState(null);
    const [selectedExpireTime, setSelectedExpireTime] = useState('7');

    useEffect(() => {
        fetch(
            backEndURL + '/api/user',
            {method: 'GET', credentials: 'include'}
        ).then((res) => { return res.json();})
            .then((responseData) => {
                setUserData(responseData)})
            .catch(error => handleErrors(error));
    }, [backEndURL]);

    const handleErrors = (error) => {
        window.location.href= `/error/400/${btoa(error)}`
    }

    const handleCreateANewBin = async () => {
        const url = backEndURL + '/api/createNewPaste';
        const data = {
            body: 'Hello World',
            expireTimeInDays: selectedExpireTime,
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

            window.location.href = "/edit/" + responseData.url;
        } catch (error) {
            console.error('Error:', error);
        }
    }

    const handleExpireTimeSelect = (e) => {
        setSelectedExpireTime(e);
    };

    return(
        <>
            <NavbarComponent></NavbarComponent>
            <div className="content-box">
                <div className="label-box">
                    <h1 className="">Welcome to the simple pasteBin!</h1>
                    <h4>Hello {userData ? userData.first_name : <>Unknown</>}!</h4>
                    <hr></hr>
                </div>

                    <div className="card-container">
                        <Card style={{width: '18rem'}}>
                            <Card.Body>
                                <Card.Title>Create a new bin</Card.Title>
                                <Card.Text>
                                    You can create {3 - (userData ? userData.binNames.length : 3)} more bins.
                                    <hr></hr>
                                    Select expire time. (In days)
                                </Card.Text>
                                <Dropdown onSelect={handleExpireTimeSelect}>
                                    <Dropdown.Toggle variant="success" id="dropdown-basic">
                                        {selectedExpireTime} days
                                    </Dropdown.Toggle>
                                    <Dropdown.Menu>
                                        <Dropdown.Item eventKey="1">1 day</Dropdown.Item>
                                        <Dropdown.Item eventKey="3">3 days</Dropdown.Item>
                                        <Dropdown.Item eventKey="7">7 days</Dropdown.Item>
                                    </Dropdown.Menu>
                                </Dropdown>
                                <hr></hr>
                                <Button variant="primary" onClick={handleCreateANewBin}
                                        disabled={!userData || userData.binNames.length - 3 >= 0}>Create!</Button>
                            </Card.Body>
                        </Card>

                        {userData ? <> <URLCards urls={userData.binNames}/> </> : <></>}
                    </div>
                <br></br>
            </div>
            <FooterComponent></FooterComponent>
        </>
    )
}

export default Profile;
