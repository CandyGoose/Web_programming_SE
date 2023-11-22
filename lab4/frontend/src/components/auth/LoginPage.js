import {useDispatch} from "react-redux";
import {Link, useNavigate} from "react-router-dom";

import {useLoginMutation} from "./authApiSlice";
import {setTokens} from "./authSlice";
import Header from "../header/Header";
import {useForm} from "react-hook-form";
import "../../css/theme.css";
import "../../css/auth.css";
import {useEffect, useState} from "react";


const LoginPage = () => {
    const dispatch = useDispatch()
    const navigate = useNavigate()

    const [login] = useLoginMutation()

    const {
        register,
        handleSubmit,
        formState: {errors},
        setValue
    } = useForm();

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        const updatedValue = value.replace(/[\t ]/g, "");
        setValue(name, updatedValue);
    };

    const validateForm = (value) => {
        const regex = /^[a-zA-Zа-яА-ЯёЁ0-9!@#$%^&*()_+\-=[\]{};':"\\|,.<>/?]*$/;
        if (!regex.test(value)) {
            const invalidChars = value.split('').filter((char, index, self) => {
                return !regex.test(char) && self.indexOf(char) === index;
            }).join(', ');
            return `Invalid character(s): ${invalidChars}`;
        }
        return true;
    };

    useEffect(() => {
        const handleStorageChange = () => {
            const numericKeys = Object.keys(localStorage).filter(key => /^\d+$/.test(key));
            if (numericKeys.length > 0) {
                window.location.reload()
            }
        };

        window.addEventListener("storage", handleStorageChange);

        return () => {
            window.removeEventListener("storage", handleStorageChange);
        };
    }, [dispatch]);

    const [error, setError] = useState(null);

    const onSubmit = async (data) => {
        try {
            const { access: accessToken, refresh: refreshToken } = await login(data).unwrap();
            dispatch(setTokens({accessToken, refreshToken, user: data.username }))
            navigate('/')
        } catch (error) {
            setError('Check login and password.');
        }
    }

    return (
        <>
            <Header pageName="Login"/>

            <form onSubmit={handleSubmit(onSubmit)}>
                <div className="input-fields">
                    <div className="input-field">
                        <label htmlFor="username">Username</label>
                        <input
                            type="text"
                            name="username"
                            placeholder="Type username"
                            {...register("username", {
                                required: "Username is required",
                                minLength: {
                                    value: 6,
                                    message: "Username should be at-least 6 characters"
                                },
                                validate: validateForm
                            })}
                            onChange={handleInputChange}
                        />
                        {errors.username && <p>{errors.username.message}</p>}
                    </div>

                    <div className="input-field">
                        <label htmlFor="password">Password</label>
                        <input
                            type="password"
                            name="password"
                            placeholder="Type password"
                            {...register("password", {
                                required: "Password is required",
                                minLength: {
                                    value: 6,
                                    message: "Password should be at-least 6 characters"
                                },
                                validate: validateForm
                            })}
                            onChange={handleInputChange}
                        />
                        {errors.password && <p>{errors.password.message}</p>}
                        {error && <p className="error-message">{error}</p>}
                    </div>

                    <div className="input-field">
                        <button>Sign In</button>
                    </div>

                    <div className="input-field">
                        <Link to="/registration">Sign Up</Link>
                    </div>
                </div>
            </form>
        </>
    )
}

export default LoginPage