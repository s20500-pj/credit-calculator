import './App.css';
import React, {useState} from 'react';
import axios from 'axios';

function App() {
    const [identifier, setIdentifier] = useState('');
    const [period, setPeriod] = useState(12);
    const [amount, setAmount] = useState(2000);
    const [response, setResponse] = useState(null);
    const [error, setError] = useState(null);

    const fetchData = (inputIdentifier, inputPeriod, inputAmount) => {
        if (inputIdentifier && inputPeriod && inputAmount) {
            axios.post('http://localhost:8080/loan/decision', {
                identifier: inputIdentifier,
                period: inputPeriod,
                amount: inputAmount
            })
                .then(res => {
                    setResponse(res.data);
                    setError(null);
                })
                .catch(err => {
                    setError({
                        message: err.response && err.response.data ? err.response.data : 'An error occurred',
                        status: err.response ? err.response.status : null
                    });
                    setResponse(null);
                });
        }
    };

    return (
        <div className="container">
            Identifier
            <input
                type="text"
                placeholder="Enter Identifier"
                value={identifier}
                onChange={(e) => {
                    const newIdentifier = e.target.value;
                    setIdentifier(newIdentifier);
                    fetchData(newIdentifier, period, amount);
                }}
            />
            <br/>

            Amount: {amount} €
            <input
                type="range"
                min={2000}
                max={10000}
                value={amount}
                onChange={(e) => {
                    const newAmount = Number(e.target.value);
                    setAmount(newAmount);
                    fetchData(identifier, period, newAmount);
                }}
            />

            <br/>

            Period: {period} months
            <input
                type="range"
                min={12}
                max={60}
                value={period}
                onChange={(e) => {
                    const newPeriod = Number(e.target.value);
                    setPeriod(newPeriod);
                    fetchData(identifier, newPeriod, amount);
                }}
            />

            <br/>

            {response && (
                <div>
        <span style={{color: response.decision ? 'green' : 'red'}}>
            Decision: {response.decision ? 'Approved' : 'Rejected'}
        </span>
                    <br/>
                    Max Amount: {response.maxAmount}
                </div>
            )}

            {response && response.requiredPeriod && (
                <div>
                    Min required period for selected amount: {response.requiredPeriod}
                </div>
            )}

            {error && (
                <div style={{color: 'red'}}>
                    {error.status && <div>Error Code: {error.status}</div>}
                    <div>{error.message}</div>
                </div>
            )}
        </div>
    );
}

export default App;
