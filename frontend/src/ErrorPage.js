import React from "react";
import {Alert, Button} from "react-bootstrap";

const ErrorPage = () => {
    return (
        <div>
            <Alert variant="danger">Something went wrong! Return to login page:</Alert>
            <Button href="/login" variant="danger">LoginPage</Button>
        </div>
    )
}

export default ErrorPage;