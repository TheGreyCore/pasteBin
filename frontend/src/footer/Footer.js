import React from "react";
import {Button} from "react-bootstrap";
import "./footer.css"

function FooterComponent() {
    return (
        <footer className="footer">
            <p>© 2024 Dmitri Matetski.  <a href="https://github.com/TheGreyCore/pasteBin/blob/master/LICENSE.md">GNU GPLv3</a></p>
            <Button variant="outline-secondary" href="https://github.com/TheGreyCore/pasteBin">GitHub</Button>
        </footer>
    );
}

export default FooterComponent;
