import React, { useEffect, useState } from 'react';

const NotificationPreferencePage = () => {
    const [accounts, setAccounts] = useState([]);
    const [preferences, setPreferences] = useState({});

    useEffect(() => {
        fetchAccounts();
    }, []);

    const fetchAccounts = async () => {
        try {
            const response = await fetch('http://localhost:8080/accounts');
            const accountData = await response.json();

            setAccounts(accountData);
        } catch (error) {
            console.error('Failed to fetch accounts:', error);
        }
    };

    const handlePreferenceChange = (accountId, preference) => {
        setPreferences((prevPreferences) => ({
            ...prevPreferences,
            [accountId]: preference,
        }));
    };

    const savePreferences = async () => {
        try {
            const accountIdPreferencePairs = Object.entries(preferences);

            for (const [accountId, preference] of accountIdPreferencePairs) {
                const emailPreference = preference ? 'true' : 'false';

                await updateNotificationPreference(accountId, emailPreference);
            }

            console.log('Preferences saved successfully!');
        } catch (error) {
            console.error('Failed to save preferences:', error);
        }
    };

    const updateNotificationPreference = async (accountId, emailPreference) => {
        try {
            const response = await fetch(
                `http://localhost:8080/update/notification/${accountId}/${emailPreference}`,
                {
                    method: 'PUT',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                }
            );

            if (!response.ok) {
                console.error('Failed to update notification preference:', response.statusText);
            }
        } catch (error) {
            console.error('Failed to update notification preference:', error);
        }
    };

    const getButtonStyle = (accountId, value) => {
        if (preferences[accountId] === value) {
            return {
                backgroundColor: 'green', // Change the background color to indicate the button is selected
            };
        }
        return {};
    };

    return (
        <div>
            <h1>Notification Preferences</h1>
            <p>Do you want to receive notifications for each account?</p>

            <table>
                <thead>
                <tr>
                    <th>Account</th>
                    <th>Notification</th>
                </tr>
                </thead>
                <tbody>
                {accounts.map((account) => (
                    <tr key={account.id}>
                        <td>{account.id}</td>
                        <td>
                            <button
                                onClick={() => handlePreferenceChange(account.id, true)}
                                style={getButtonStyle(account.id, true)}
                            >
                                Yes
                            </button>
                            <button
                                onClick={() => handlePreferenceChange(account.id, false)}
                                style={getButtonStyle(account.id, false)}
                            >
                                No
                            </button>
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

            <button onClick={savePreferences}>Save Preferences</button>
        </div>
    );
};

export default NotificationPreferencePage;




