import React, { createContext, useState, useEffect, useContext } from 'react';
import { User } from '../types';
import { v4 as uuidv4 } from 'uuid';

interface AuthContextType {
  currentUser: User | null;
  isAuthenticated: boolean;
  login: (email: string, password: string) => Promise<User>;
  logout: () => void;
  register: (username: string, email: string, password: string) => Promise<User>;
  loading: boolean;
  error: string | null;
}

const AuthContext = createContext<AuthContextType | undefined>(undefined);

export const useAuth = () => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};

const generateAvatar = (username: string): string => {
  const colors = [
    'bg-purple-500', 'bg-blue-500', 'bg-green-500', 
    'bg-yellow-500', 'bg-red-500', 'bg-pink-500',
    'bg-indigo-500', 'bg-teal-500'
  ];
  
  // Use the username to select a consistent color
  const colorIndex = username.charCodeAt(0) % colors.length;
  return colors[colorIndex];
};

export const AuthProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const [currentUser, setCurrentUser] = useState<User | null>(null);
  const [loading, setLoading] = useState<boolean>(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    // Check if user is stored in local storage
    const storedUser = localStorage.getItem('chatUser');
    if (storedUser) {
      try {
        setCurrentUser(JSON.parse(storedUser));
      } catch (e) {
        console.error('Failed to parse stored user:', e);
        localStorage.removeItem('chatUser');
      }
    }
    setLoading(false);
  }, []);

  const login = async (email: string, password: string): Promise<User> => {
    setLoading(true);
    setError(null);
    
    try {
      // Simulate API call delay
      await new Promise(resolve => setTimeout(resolve, 800));
      
      // In a real app, this would be an API call to validate credentials
      const storedUsers = localStorage.getItem('chatUsers');
      const users: User[] = storedUsers ? JSON.parse(storedUsers) : [];
      
      const user = users.find(u => u.email === email);
      
      if (!user) {
        throw new Error('User not found');
      }
      
      // In a real app, you would validate the password with bcrypt
      // For this demo, we're just checking if the user exists
      
      // Update user's online status
      const updatedUser = { ...user, isOnline: true };
      
      // Update the user in the local storage users array
      const updatedUsers = users.map(u => 
        u.id === updatedUser.id ? updatedUser : u
      );
      
      localStorage.setItem('chatUsers', JSON.stringify(updatedUsers));
      localStorage.setItem('chatUser', JSON.stringify(updatedUser));
      
      setCurrentUser(updatedUser);
      return updatedUser;
    } catch (err) {
      const errorMessage = err instanceof Error ? err.message : 'Failed to login';
      setError(errorMessage);
      throw new Error(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const register = async (username: string, email: string, password: string): Promise<User> => {
    setLoading(true);
    setError(null);
    
    try {
      // Simulate API call delay
      await new Promise(resolve => setTimeout(resolve, 800));
      
      // In a real app, this would be an API call to create a user
      const storedUsers = localStorage.getItem('chatUsers');
      const users: User[] = storedUsers ? JSON.parse(storedUsers) : [];
      
      if (users.some(u => u.email === email)) {
        throw new Error('Email already in use');
      }
      
      // Create new user
      const newUser: User = {
        id: uuidv4(),
        username,
        email,
        avatar: generateAvatar(username),
        isOnline: true,
        lastSeen: new Date().toISOString()
      };
      
      // Save to "database" (localStorage)
      users.push(newUser);
      localStorage.setItem('chatUsers', JSON.stringify(users));
      localStorage.setItem('chatUser', JSON.stringify(newUser));
      
      setCurrentUser(newUser);
      return newUser;
    } catch (err) {
      const errorMessage = err instanceof Error ? err.message : 'Failed to register';
      setError(errorMessage);
      throw new Error(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const logout = () => {
    if (currentUser) {
      // Update user's online status
      const storedUsers = localStorage.getItem('chatUsers');
      if (storedUsers) {
        const users: User[] = JSON.parse(storedUsers);
        const updatedUsers = users.map(u => 
          u.id === currentUser.id 
            ? { ...u, isOnline: false, lastSeen: new Date().toISOString() } 
            : u
        );
        localStorage.setItem('chatUsers', JSON.stringify(updatedUsers));
      }
      
      localStorage.removeItem('chatUser');
      setCurrentUser(null);
    }
  };

  return (
    <AuthContext.Provider value={{ 
      currentUser, 
      isAuthenticated: !!currentUser, 
      login, 
      logout, 
      register,
      loading,
      error
    }}>
      {children}
    </AuthContext.Provider>
  );
};