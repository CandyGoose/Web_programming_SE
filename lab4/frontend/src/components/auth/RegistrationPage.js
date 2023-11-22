import {useForm} from "react-hook-form";
import {Link, useNavigate} from "react-router-dom";

import {useRegistrationMutation} from "./authApiSlice";
import Header from "../header/Header";
import "../../css/theme.css";
import "../../css/auth.css";
import {useState} from "react";

const RegistrationPage = () => {
    const navigate = useNavigate()

    const [registration] = useRegistrationMutation();

    const {
        register,
        handleSubmit,
        formState: { errors },
        getValues,
        setValue
    } = useForm();

    const [error, setError] = useState(null);

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

    const handleInputChange = (event) => {
        const { name, value } = event.target;
        const updatedValue = value.replace(/[\t ]/g, "");
        setValue(name, updatedValue);
    };

    const onSubmit = async (data) => {
        try {
            const result = await registration(data);
            if (result.error && result.error.status === 401) {
                setError('A user with this name already exists');
            } else if (result.error) {
                setError(result.error.message || 'Error occurred.');
            } else {
                navigate("/login");
            }
        } catch (error) {
            setError('Error occurred.');
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
                                    },
                                    maxLength: {
                                        value: 30,
                                        message: "Password should be less than 30 characters"
                                    },
                                    validate: validateForm
                                })}
                                onChange={handleInputChange}
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
                                    },
                                    maxLength: {
                                        value: 30,
                                        message: "Password should be less than 30 characters"
                                    },
                                    validate: validateForm
                                })}
                                onChange={handleInputChange}
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
                                onChange={handleInputChange}
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