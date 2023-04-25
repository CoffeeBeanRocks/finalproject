import Image from 'next/image'
import { Inter } from 'next/font/google'
import { useState, useEffect } from 'react';

const inter = Inter({ subsets: ['latin'] })


function BankAccountPage() {
    const [accounts, setAccounts] = useState([]);
    const [selectedAccount, setSelectedAccount] = useState(null);
    const [balance, setBalance] = useState(null);
    const [transferAmount, setTransferAmount] = useState(null);

    useEffect(() => {
        // Fetch list of accounts from API
        fetch('/api/accounts')
            .then(response => response.json())
            .then(data => setAccounts(data))
            .catch(error => console.error(error));
    }, []);

    useEffect(() => {
        if (selectedAccount) {
            // Fetch account balance for selected account from API
            fetch(`/api/accounts/${selectedAccount.id}/balance`)
                .then(response => response.json())
                .then(data => setBalance(data.balance))
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
            // Send transfer request to API
            fetch(`/api/accounts/${selectedAccount.id}/transfer`, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ amount: transferAmount })
            })
                .then(response => response.json())
                .then(data => {
                    console.log('Transfer successful');
                    // Refresh account and balance data
                    setSelectedAccount(selectedAccount);
                })
                .catch(error => console.error(error));
        }
    }

    return (
        <div>
            <h1>Bank Accounts</h1>
            <ul>
                {accounts.map(account => (
                    <li key={account.id} onClick={() => handleAccountSelect(account)}>
                        {account.name}
                    </li>
                ))}
            </ul>
            {selectedAccount && (
                <div>
                    <h2>{selectedAccount.name}</h2>
                    <p>Balance: {balance}</p>
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