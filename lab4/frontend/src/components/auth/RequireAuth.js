import React, { useState, useEffect } from "react";
import { useLocation, Navigate, Outlet } from "react-router-dom";
import { useSelector } from "react-redux";
import { selectAccessToken, selectRefreshToken } from "./authSlice";

const RequireAuth = () => {
    const accessToken = useSelector(selectAccessToken);
    const refreshToken = useSelector(selectRefreshToken);
    const location = useLocation();
    const [redirect, setRedirect] = useState(false);

    useEffect(() => {
        const timer = setTimeout(() => {
            if (!accessToken || !refreshToken) {
                setRedirect(true);
            }
        }, 100);

        return () => clearTimeout(timer);
    }, []);

    return (
        redirect ? (
            <Navigate to="/login" state={{ from: location }} replace />
        ) : (
            (accessToken && refreshToken) ? <Outlet /> : null
        )
    );
};

export default RequireAuth;
