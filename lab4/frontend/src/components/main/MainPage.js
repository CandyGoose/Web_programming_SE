import {useForm} from "react-hook-form";
import {MultiSelect} from 'primereact/multiselect';
import 'primeicons/primeicons.css';
import 'primereact/resources/themes/lara-light-indigo/theme.css';
import 'primereact/resources/primereact.css';
import 'primeflex/primeflex.css';


import {useCheckCoordinatesMutation, useGetResultsQuery, useClearResultsMutation} from "./mainApiSlice";
import Header from "../header/Header";
import "../../css/theme.css";
import "../../css/main.css";
import ChartView from "./ChartView";
import {useEffect, useState} from "react";

const MainPage = () => {


    const [selectedX, setSelectedX] = useState(0.0)
    let [selectedR, setSelectedR] = useState(0.0)
    const [lastR, setLastR] = useState(null)
    const [changeX, setChangeX] = useState(false);
    const [clearResults] = useClearResultsMutation();


    const {
        data: results,
        isLoading,
        isSuccess,
        isError,
        refetch: refetchResults
    } = useGetResultsQuery()
    const [checkCoordinates] = useCheckCoordinatesMutation()

    const { register,
        handleSubmit,
        formState: { errors },
        setError,
        clearErrors
    } = useForm();

    const [isPageFocused, setIsPageFocused] = useState(true);

    const handleVisibilityChange = () => {
        setIsPageFocused(!document.hidden);
    };

    useEffect(() => {
        document.addEventListener('visibilitychange', handleVisibilityChange);

        return () => {
            document.removeEventListener('visibilitychange', handleVisibilityChange);
        };
    }, []);

    useEffect(() => {
        const fetchData = async () => {
            await refetchResults();
        };

        fetchData();

        window.addEventListener('focus', function() {
            fetchData();
        });
    }, [isPageFocused]);

    const onSubmit = async (data) => {
        setChangeX(true);
        const selectedXValues = selectedX || [];
        const selectedRValues = selectedR || [];

        // Y coordinate
        const y = data.y.replace(/,/g, '.').replace(/^\+/, '');

        for (let i = 0; i < selectedRValues.length; i++) {
            for (let j = 0; j < selectedXValues.length; j++) {
                await checkCoordinates({x: selectedXValues[j], y, r: selectedRValues[i]})
            }
        }

        refetchResults()

    }

    const handleXChanged = (e) => {
        setChangeX(true);
        setSelectedX(e.value);
    };


    const handleRChanged = (e) => {
        const updateSelectedR = (value) => {
            if (value.length > 1) {
                value = value.slice(1);
            }
            setSelectedR(value);
            setLastR(value[0]);
        };

        updateSelectedR(e.value);
    };

    useEffect(() => {
        if ((!selectedX || selectedX.length === 0) && changeX) {
            setError("x", { type: "manual", message: "X must be selected" });
        } else if (changeX) {
            clearErrors("x");
            setChangeX(false);
        }
     if (!selectedR || selectedR.length === 0) {
            setError("r", { type: "manual", message: "R must be selected" });
        } else if (selectedR.length > 1) {
            setError("r", { type: "manual", message: "R more than 1" });
        } else {
            clearErrors("r");
        }
    }, [selectedX, changeX, selectedR]);

    const handleClickChart = async (coordinate) => {
        const selectedRValues = selectedR || [];

        if (selectedRValues.length === 0) {
            setError("r", { type: "manual", message: "R must be selected" });
            return;
        } else if (selectedRValues.length > 1) {
            setError("r", { type: "manual", message: "R more than 1" });
            return;
        } else {
            clearErrors("r");
        }

        for (const rValue of selectedRValues) {
            await checkCoordinates({ ...coordinate, r: rValue });
        }

        refetchResults();
    };

    const handleClearTable = async () => {
        try {
            await clearResults();
            await refetchResults();
        } catch (error) {
        }
    };

    const xItems = [
        { label: '-3', value: -3.0 },
        { label: '-2', value: -2.0 },
        { label: '-1', value: -1.0 },
        { label: '0', value: 0.0 },
        { label: '1', value: 1.0 },
        { label: '2', value: 2.0 },
        { label: '3', value: 3.0 },
        { label: '4', value: 4.0 },
        { label: '5', value: 5.0 },
    ];


    const rItems = [
        { label: '-3', value: -3.0 },
        { label: '-2', value: -2.0 },
        { label: '-1', value: -1.0 },
        { label: '0', value: 0.0 },
        { label: '1', value: 1.0 },
        { label: '2', value: 2.0 },
        { label: '3', value: 3.0 },
        { label: '4', value: 4.0 },
        { label: '5', value: 5.0 },
    ];

    const mapResultsToChartItems = () => {
        return results && Array.isArray(results)
            ? results
                .filter(res => parseFloat(res.r) === lastR)
                .map(res => ({
                    x: parseFloat(res.x),
                    y: parseFloat(res.y),
                    r: parseFloat(res.r),
                    color: res.success ? "green" : "red"
                }))
            : [];
    };

    const formatTimestamp = (timestamp) => {
        const date = new Date(timestamp);
        return `${date.getFullYear()}-${padZero(date.getMonth() + 1)}-${padZero(date.getDate())} ${padZero(date.getHours())}:${padZero(date.getMinutes())}:${padZero(date.getSeconds())}`;
    };

    const padZero = (value) => {
        return String(value).padStart(2, '0');
    };

    return <>
        <Header pageName="Main page"/>
        <div className="main">
            <h1 className="main__title">Web-programming, Lab #4</h1>

            <div className="main__row">
                <div className="main__left-block">
                    <div className="graph">
                        {isLoading && <div>Loading...</div>}
                        {isError && <p>Something went wrong...</p>}
                        {isSuccess && <ChartView
                            width={300} height={300}
                            minX={-6} maxX={6}
                            minY={-6} maxY={6}
                            radius={lastR}
                            items={mapResultsToChartItems()}
                            onClickChart={handleClickChart}
                        />}
                    </div>

                    <div className="form">
                        <form onSubmit={handleSubmit(onSubmit)}>
                            <div className="form-inputs">
                                <div className="form__row">
                                    <label htmlFor="X" className="form__label">X</label>
                                    <div className="x-coord form__row">
                                        <MultiSelect
                                            value={selectedX}
                                            options={xItems}
                                            onChange={handleXChanged}
                                            placeholder="Select X"
                                        />
                                        {errors.x && <p>{errors.x.message}</p>}
                                    </div>
                                </div>

                                <div className="form__row">
                                    <label htmlFor="y" className="form__label">Y</label>
                                    <div className="y-coord form__row">
                                        <input
                                            type="text"
                                            name="y"
                                            placeholder="Enter Y in the range (-5;3)"
                                            {...register("y", {
                                                pattern: {
                                                    value: /^[+-]?([0-9]*[.,])?[0-9]+$/,
                                                    message: "Check y value"
                                                },
                                                validate: (value) => {
                                                    if (!/^[+-]?(?:[0-2]|-[1-4])(?:[.,]\d+)?$/.test(value)) {
                                                        setChangeX(true);
                                                        return "Y must be in (-5;3)";
                                                    }
                                                    return true;
                                                }
                                            })}
                                        />
                                        {errors.y && <p>{errors.y.message}</p>}
                                    </div>
                                </div>

                                <div className="form__row">
                                    <label className="form__label">R</label>
                                    <div className="r-coord form__row">
                                        <MultiSelect
                                            value={selectedR}
                                            options={rItems}
                                            onChange={handleRChanged}
                                            placeholder="Select R"
                                        />
                                        {errors.r && <p>{errors.r.message}</p>}
                                    </div>
                                </div>
                            </div>

                            <div className="form__row form__btn-row">
                                <button>Check</button>
                                <button type="button" onClick={() => handleClearTable()}>Clear Table</button>
                            </div>
                        </form>
                    </div>
                </div>

                <div className="main__table-block">
                    {isLoading && <div>Loading...</div>}
                    {isError && <p>Something went wrong...</p>}
                    {isSuccess && <table>
                        <thead>
                        <tr>
                            <th>X</th>
                            <th>Y</th>
                            <th>R</th>
                            <th>Result</th>
                            <th>Time</th>
                        </tr>
                        </thead>
                        <tbody>
                        {results.map((result) => {
                            return <tr key={result.id}>
                                <td>{result.x.toString().replace('.', ',')}</td>
                                <td>{result.y.toString().replace('.', ',')}</td>
                                <td>{result.r.toString().replace('.', ',')}</td>
                                <td style={{ color: result.success ? "green" : "red" }}>
                                    {result.success ? "hit" : "miss"}
                                </td>
                                <td>{formatTimestamp(result.timestamp)}</td>
                            </tr>
                        })}
                        </tbody>
                    </table>}
                </div>
            </div>
        </div>
    </>
}

export default MainPage