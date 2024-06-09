import React, {useEffect, useState} from 'react';
import {Button, Card, Dropdown} from "react-bootstrap";

const SuccesfullPage = () => {
    const [userData, setUserData] = useState(null);
    const [selectedExpireTime, setSelectedExpireTime] = useState('7');

    useEffect(() => {
        fetch(
            'http://localhost:8080/api/user',
            {method: 'GET', credentials: 'include'}
        ).then((res) => { return res.json();})
            .then((responseData) => {
                console.log(responseData)
                setUserData(responseData)})
            .catch(error => handleErrors(error));
    }, []);

    const handleErrors = (error) => {
        console.error('Error:', error);
        //window.location.href = "/error";
    }

    const handleCreateANewBin = async () => {
        const url = 'http://localhost:8080/api/createNewBin';
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

            if (!response.ok) {
                throw new Error(`HTTP error! status: ${response.status}`);
            }
            const responseData = await response.json();
            window.location.href = "/edit/" + responseData.url;
        } catch (error) {
            console.error('Error:', error);
        }
    }

    const handleExpireTimeSelect = (e) => {
        setSelectedExpireTime(e);
    };

    return(
        <div>
            <div className="content-box">
                <div className="label-box">
                    <h1 className="">Welcome to the simple pasteBin!</h1>
                    <br></br>
                    <h5>Hello {userData ? userData.first_name: <>Unknown</>}!</h5>
                    <hr></hr>
                    <Card style={{ width: '18rem' }}>
                        <Card.Body>
                            <Card.Title>Create a new bin</Card.Title>
                            <Card.Text>
                                You can create {3-(userData ? userData.binNames.length: 0)} more bins.
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
                            <Button variant="primary" onClick={handleCreateANewBin} disabled={!userData || userData.binNames.length - 3 >= 0}>Create!</Button>
                        </Card.Body>
                    </Card>
                </div>
            </div>
        </div>
    )
}

export default SuccesfullPage;
