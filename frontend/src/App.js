import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import EditPage from "./EditPage";
import MainPage from "./mainpage/MainPage";
import ErrorPage from "./ErrorPage";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/edit" Component={EditPage} />
                <Route path="*" Component={MainPage} />
                <Route path="/error" Component={ErrorPage} />
            </Routes>
        </Router>
    );
}

export default App;
