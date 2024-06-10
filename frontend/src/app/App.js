import 'bootstrap/dist/css/bootstrap.min.css';
import {BrowserRouter as Router, Route, Routes} from "react-router-dom";
import EditPage from "../edit/EditPage";
import MainPage from "../mainpage/MainPage";
import ErrorPage from "../ErrorPage";
import Profile from "../profile/Profile";

function App() {
    return (
        <Router>
            <Routes>
                <Route path="/edit/:binURL" Component={EditPage} />
                <Route path="*" Component={MainPage} />
                <Route path="/error" Component={ErrorPage} />
                <Route path="/profile" Component={Profile} />
            </Routes>
        </Router>
    );
}

export default App;
