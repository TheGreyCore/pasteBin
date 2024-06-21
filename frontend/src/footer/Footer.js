import React from "react";
import {Button} from "react-bootstrap";
import "./footer.css"

function FooterComponent() {
    return (
        <footer className="footer">
            <p>© 2024 Dmitri Matetski. All Rights Reserved.</p>
            <Button variant="outline-secondary" href="">GitHub</Button>
        </footer>
    );
}

export default FooterComponent;
