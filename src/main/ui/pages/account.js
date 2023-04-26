import { useState, useEffect } from 'react';

function BankAccountPage() {
    const [bankAccounts, setBankAccounts] = useState([]);
    const [selectedAccount, setSelectedAccount] = useState(null);
    const [accountBalance, setAccountBalance] = useState(null);
    const [transferAmount, setTransferAmount] = useState(null);

    useEffect(() => {
        fetch("http://localhost:8080/accounts")
            .then(response => response.json())
            .then(data =>
                {
                    console.log(data);
                    setBankAccounts(data);
                }
            )
            .catch(error => console.error(error));
    }, []);

    useEffect(() => {
        if (selectedAccount) {
            fetch("/create/{email}/{password}/{sendEmail}")
                .then(response => response.json())
                .then(data => setAccountBalance(data.balance))
                .catch(error => console.error(error));
        }
    }, [selectedAccount]);

    function handleAccountSelect(account) {
        setSelectedAccount(account);
    }

    function handleTransferAmountChange(event) {
        setTransferAmount(event.target.value);
    }

    function handleTransferSubmit(event) {
        event.preventDefault();

        if (selectedAccount && transferAmount) {
            fetch("/transfer/{recipientEmail}/{myEmail}/{myPassword}/{amount}", {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ amount: transferAmount })
            })
                .then(response => response.json())
                .then(data => {
                    console.log('Transfer successful');
                    setSelectedAccount(selectedAccount);
                })
                .catch(error => console.error(error));
        }
    }

    return (
        <div>
            <h1>Bank Accounts</h1>
            <ul>
                {bankAccounts.map(account => (
                    <li key={account.id} onClick={() => handleAccountSelect(account)}>
                        {account.id}
                    </li>
                ))}
            </ul>
            {selectedAccount && (
                <div>
                    <h2>{selectedAccount.name}</h2>
                    <p>Balance: {accountBalance}</p>
                    <form onSubmit={handleTransferSubmit}>
                        <label>
                            Transfer Amount:
                            <input type="number" value={transferAmount} onChange={handleTransferAmountChange} />
                        </label>
                        <button type="submit">Transfer</button>
                    </form>
                </div>
            )}
        </div>
    );
}

export default BankAccountPage;