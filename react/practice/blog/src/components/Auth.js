import React, {useState, useEffect} from 'react';
import TextField from '@mui/material/TextField';
import Stack from '@mui/material/Stack';
import Button from '@mui/material/Button';
import {auth} from '../firebase';

export default function Auth() {
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [user, setUser] = useState('');

    let create = async () => {
        let res = await auth.createUserWithEmailAndPassword(email, password);
    }

    let logOut = async () => {
        await auth.signOut();
    }

    let signIn = async () => {
        await auth.signInWithEmailAndPassword(email, password);
    }

    useEffect(() => {
        let unsubscribe = auth.onAuthStateChanged((user) => {
            setUser(user);

            return () => {
                unsubscribe(); // cleanup while component will unmount
            }
        }, [])
    })

    return (
        <>  
            <div className="auth-wrap">
                <div className="auth-wrap-inner">
                    {
                        user === null ?

                        <>
                            <h1>Dear User Login</h1>
                            <TextField required id="email" label="Email" style={{maxWidth: "300px", width: "100%", marginBottom: "20px"}} onChange={(e) => setEmail(e.target.value)}/>
                            <TextField required id="password" label="password" type="password" style={{maxWidth: "300px", width: "100%"}} onChange={(e) => setPassword(e.target.value)}/>
                            <Button variant="contained" color="success" type="submit" style={{marginTop: "30px", maxWidth: "300px", width: "100%"}} onClick={signIn}>Login</Button>
                        </> :
                        <>
                            <h1>Do want to logout?</h1>
                            <Button variant="outlined" color="error" onClick={logOut}>LogOut</Button>
                        </>
                    }
                </div>
            </div>:
        </>
    )
}
