import { useState } from 'react';

function TransferPage() {
    const [recipientEmail, setRecipientEmail] = useState('');
    const [transferAmount, setTransferAmount] = useState(null);
    const [transferStatus, setTransferStatus] = useState(null);

    function handleRecipientEmailChange(event) {
        setRecipientEmail(event.target.value);
    }

    function handleTransferAmountChange(event) {
        setTransferAmount(event.target.value);
    }

    function handleTransferSubmit(event) {
        event.preventDefault();

        if (recipientEmail && transferAmount) {
            fetch('http://localhost:8080/api/transfers', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ recipientEmail, transferAmount })
            })
                .then(response => response.json())
                .then(data => {
                    setTransferStatus(`Transfer successful: ${data.message}`);
                    setRecipientEmail('');
                    setTransferAmount(null);
                })
                .catch(error => {
                    console.error(error);
                    setTransferStatus('Transfer failed');
                });
        }
    }

    return (
        <div>
            <h1>Transfer Money</h1>
            <form onSubmit={handleTransferSubmit}>
                <label>
                    Recipient Email:
                    <input type="email" value={recipientEmail} onChange={handleRecipientEmailChange} />
                </label>
                <label>
                    Transfer Amount:
                    <input type="number" value={transferAmount} onChange={handleTransferAmountChange} />
                </label>
                <button type="submit">Send</button>
            </form>
            {transferStatus && <p>{transferStatus}</p>}
        </div>
    );
}

export default TransferPage;