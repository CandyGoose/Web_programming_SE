import {useForm} from "react-hook-form";
import {Link, useNavigate} from "react-router-dom";

import {useRegistrationMutation} from "./authApiSlice";
import Header from "../header/Header";
import "../../css/theme.css";
import "../../css/auth.css";
import {useState} from "react";
import {setTokens} from "./authSlice";

const RegistrationPage = () => {
    const navigate = useNavigate()

    const [registration, { isSuccess }] = useRegistrationMutation();

    const {
        register,
        handleSubmit,
        formState: { errors },
        getValues
    } = useForm();

    const [error, setError] = useState(null);

    const onSubmit = async (data) => {
        try {
            if (isSuccess) {
                await registration(data);
                navigate("/login");
            } else {
                setError('The user already exists.');
            }
        } catch (error) {
            setError('Error.');
        }
    }

    return (
        <>
            <div className="login-container">
                <Header pageName="Registration"/>

                <form onSubmit={handleSubmit(onSubmit)}>
                    <div className="input-fields">
                        <div className="input-field">
                            <label className="input-label" htmlFor="username">Username</label>
                            <input
                                type="text"
                                name="username"
                                placeholder="Type username"
                                {...register("username", {
                                    required: "Username is required",
                                    minLength: {
                                        value: 6,
                                        message: "Username should be at-least 6 characters"
                                    }
                                })}
                            />
                            {errors.username && <p>{errors.username.message}</p>}
                        </div>

                        <div className="input-field">
                            <label htmlFor="password1">Password</label>
                            <input
                                type="password"
                                name="password1"
                                placeholder="Type password"
                                {...register("password1", {
                                    required: "Password is required",
                                    minLength: {
                                        value: 6,
                                        message: "Password should be at-least 6 characters"
                                    }
                                })}
                            />
                            {errors.password1 && <p>{errors.password1.message}</p>}
                        </div>


                        <div className="input-field">
                            <label htmlFor="password2">Password confirmation</label>
                            <input
                                type="password"
                                name="password2"
                                placeholder="Type password confirmation"
                                {...register("password2", {
                                    required: "Password confirmation is required",
                                    validate: (value) => getValues("password1") === value
                                        ? true
                                        : "Password and password confirmation must be equal"
                                })}
                            />
                            {errors.password2 && <p>{errors.password2.message}</p>}
                            {error && <p className="error-message">{error}</p>}
                        </div>

                        <div className="input-field">
                            <button>Sign Up</button>
                        </div>

                        <div  className="input-field">
                            <Link to="/login">Sign In</Link>
                        </div>
                    </div>
                </form>
            </div>
        </>
    )
}

export default RegistrationPage