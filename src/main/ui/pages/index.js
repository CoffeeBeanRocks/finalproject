import Image from 'next/image'
import { Inter } from 'next/font/google'
import { useRouter } from 'next/router'
const inter = Inter({ subsets: ['latin'] })

export default function Home() {
    const router = useRouter()

    const handleSignIn = () => {
        router.push('/account')
    }

    return (
        <>
            <div className="login-form">
                <form>
                    <h1>Login</h1>

                    <div className="content">
                        <div className="input-field">
                            <input type="email" placeholder="Email" autoComplete="nope"></input>
                        </div>
                        <div className="input-field">
                            <input type="password" placeholder="Password" autoComplete="new-password"></input>
                        </div>
                    </div>

                    <div className="action">
                        <button>Register</button>
                        <button onClick={handleSignIn}>Sign in</button>
                    </div>
                </form>
            </div>
            <script src="./script.js"></script>
        </>
    )
}


