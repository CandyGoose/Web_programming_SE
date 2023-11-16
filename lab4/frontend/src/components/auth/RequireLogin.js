import { useLocation, Navigate, Outlet } from "react-router-dom"
import { useSelector } from "react-redux"
import { selectAccessToken, selectRefreshToken } from "./authSlice"

const RequireLogin = () => {
    const accessToken = useSelector(selectAccessToken)
    const refreshToken = useSelector(selectRefreshToken)

    const location = useLocation()

    return (
        accessToken && refreshToken
            ? <Navigate to="/" state={{ from: location }} replace />
            : <Outlet />
    )
}

export default RequireLogin