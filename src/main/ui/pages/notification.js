import { useState, useEffect } from 'react';
import { useRouter } from 'next/router';

function NotificationPage() {
    const router = useRouter();
    const [notifications, setNotifications] = useState([]);

    useEffect(() => {
        const { query } = router;
        const accountId = query.accountId;

        if (accountId) {
            fetch(`http://localhost:3000/allNotifications?accountId=${accountId}`)
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    setNotifications(data);
                })
                .catch(error => console.error(error));
        } else {
            fetch("http://localhost:3000/allNotifications")
                .then(response => response.json())
                .then(data => {
                    console.log(data);
                    setNotifications(data);
                })
                .catch(error => console.error(error));
        }
    }, []);

    return (
        <div>
            <h1>Notifications</h1>
            {notifications.map(notification => (
                <div key={notification.id}>
                    <h2>{notification.title}</h2>
                    <p>{notification.message}</p>
                </div>
            ))}
        </div>
    );
}

export default NotificationPage;
