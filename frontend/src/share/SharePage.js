import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";

const SharePage= () => {
    const params = useParams();
    const [fileContent, setFileContent] = useState(null);

    useEffect(() => {
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

    return (
        <div>
            <div className="content-box">
                <h1 className="">Easy pasteBin</h1>
                <hr></hr>
                <h6>File content:</h6>
                <textarea
                    className="form-control"
                    id="content-textarea"
                    rows="5"
                    disabled
                    value={fileContent}
                    onChange={e => setFileContent(e.target.value)}
                />
            </div>
        </div>
    );

}

export default SharePage;