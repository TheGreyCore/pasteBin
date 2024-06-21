import React from 'react';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import "./URLCard.css"

let backEndURL = "http://localhost:8080";
let frontEndURL = "http://localhost:3000";


const URLCard = ({ url, onEdit, onRemove, onView, number}) => (
    <Card style={{ width: '18rem', marginTop: '10px' }}>
        <Card.Body>
            <Card.Title>Your {number}. pasteBin</Card.Title>
            <Button variant="primary" onClick={() => onEdit(url)}>Edit</Button>
            <Button variant="success" onClick={() => onView(url)} style={{ marginLeft: '10px' }}>View</Button>
            <Button variant="danger" onClick={() => onRemove(url)} style={{ marginLeft: '10px' }}>Remove</Button>
        </Card.Body>
    </Card>
);

const URLCards = ({ urls }) => {
    const handleEdit = (url) => {
        window.location.href = "/edit/" + url;
    };

    const handleDelete = async (url) => {
        const confirmDelete = window.confirm('Are you sure you want to delete this bin?');
        if (confirmDelete) {
            await fetch(
                backEndURL + "/api/deletePaste?url=" + url,
                {method: 'DELETE', credentials: 'include'}
            ).then((res) => {
                window.location.href = "/profile"
            });
        }
    }

    const handleView = (url) => {
        window.location.href = frontEndURL + "/share/" + url
    }

    return (
        <div>
            {urls.map((url, index) => (
                <URLCard className key={index} url={url} onEdit={handleEdit} onRemove={handleDelete} onView={handleView} number={index + 1}/>
            ))}
        </div>
    );
};

export default URLCards;
