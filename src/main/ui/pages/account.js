import { useState, useEffect } from 'react';

function BankAccountPage() {
    const [bankAccounts, setBankAccounts] = useState([]);
    const [selectedAccount, setSelectedAccount] = useState(null);

    useEffect(() => {
        fetch("http://localhost:8080/accounts")
            .then(response => response.json())
            .then(data => {
                console.log(data);
                setBankAccounts(data);
            })
            .catch(error => console.error(error));
    }, []);

    function handleAccountSelect(account) {
        setSelectedAccount(account);
    }

    function handleTransferClick() {
        if (selectedAccount) {
            window.location.href = '/transfer';
        }
    }

    return (
        <div>
            <h1>Bank Accounts</h1>
            <div style={{ border: '2px solid blue', padding: '10px' }}>
                <table>
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Name</th>
                        <th>Balance</th>
                    </tr>
                    </thead>
                    <tbody>
                    {bankAccounts.map(account => (
                        <tr key={account.id} onClick={() => handleAccountSelect(account)}>
                            <td>{account.id}</td>
                            <td>{account.name}</td>
                            <td>{account.balance}</td>
                        </tr>
                    ))}
                    </tbody>
                </table>
                {selectedAccount && (
                    <div>
                        <h2>{selectedAccount.name}</h2>
                        <table>
                            <thead>
                            <tr>
                                <th>Account ID</th>
                                <th>Balance</th>
                                <th></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td>{selectedAccount.id}</td>
                                <td>{selectedAccount.balance}</td>
                                <td>
                                    <button onClick={handleTransferClick}>Transfer Money</button>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </div>
                )}
            </div>
        </div>
    );
}

export default BankAccountPage;