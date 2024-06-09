import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import EditPage from "./EditPage";
import MainPage from "./mainpage/MainPage";
import ErrorPage from "./ErrorPage";
import SuccessfullPage from "./successfullpage/SuccessfullPage";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/edit/:binURL" Component={EditPage} />
                <Route path="*" Component={MainPage} />
                <Route path="/error" Component={ErrorPage} />
                <Route path="/successfully" Component={SuccessfullPage} />
            </Routes>
        </Router>
    );
}

export default App;
