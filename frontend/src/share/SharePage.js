import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import NavbarComponent from "../navbar/Navbar";
import FooterComponent from "../footer/Footer";

const SharePage= () => {
    let backEndURL = "http://localhost:8080";
    const params = useParams();
    const [fileContent, setFileContent] = useState(null);

    useEffect(() => {
        fetch(
            backEndURL + "/api/getPasteByURL?url=" + params.binURL,
            {method: 'GET'}
        ).then((res) => { return res.json();})
            .then((responseData) => {
                setFileContent(responseData.body)})
            .catch(error => handleErrors(error));
    }, [backEndURL, params.binURL]);

    const handleErrors = (error) => {
        window.location.href= `/error/400/${btoa(error)}`
    }

    return (
        <>
            <NavbarComponent></NavbarComponent>
            <div className="content-box">
                <textarea
                    className="form-control"
                    id="content-textarea"
                    rows="30"
                    disabled
                    value={fileContent}
                    onChange={e => setFileContent(e.target.value)}
                />
            </div>
            <FooterComponent></FooterComponent>
        </>
    );

}

export default SharePage;