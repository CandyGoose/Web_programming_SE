import React from 'react';
import {Route, Routes} from "react-router-dom";

import LoginPage from "./components/auth/LoginPage";
import RegistrationPage from "./components/auth/RegistrationPage";
import RequireAuth from "./components/auth/RequireAuth";
import MainPage from "./components/main/MainPage";
import RequireLogin from "./components/auth/RequireLogin";


function App() {
  return (
      <Routes>
          <Route element={<RequireAuth />}>
              <Route index element={<MainPage />} />
          </Route>
          <Route element={<RequireLogin />}>
              <Route path="/login" element={<LoginPage />} />
              <Route path="/registration" element={<RegistrationPage />} />
          </Route>
      </Routes>
  );
}

export default App;
