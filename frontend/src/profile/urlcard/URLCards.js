import React from 'react';
import Card from 'react-bootstrap/Card';
import Button from 'react-bootstrap/Button';
import "../Profile.css"

const URLCard = ({ url, onEdit, onRemove, number}) => (
    <Card style={{ width: '18rem', marginTop: '10px' }}>
        <Card.Body>
            <Card.Title>Your {number}. pasteBin</Card.Title>
            <Button variant="primary" onClick={() => onEdit(url)}>Edit</Button>
            <Button variant="danger" onClick={() => onRemove(url)} style={{ marginLeft: '10px' }}>Remove</Button>
        </Card.Body>
    </Card>
);

const URLCards = ({ urls }) => {
    const handleEdit = (url) => {
        window.location.href = "/edit/"+url;
    };

    const handleRemove = (url) => {
        // Handle remove action here
        console.log(`Remove: ${url}`);
    };

    return (
        <div>
            {urls.map((url, index) => (
                <URLCard className key={index} url={url} onEdit={handleEdit} onRemove={handleRemove} number={index + 1}/>
            ))}
        </div>
    );
};

export default URLCards;
