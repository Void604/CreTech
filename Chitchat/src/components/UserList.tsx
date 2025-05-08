import React from 'react';
import { User, Conversation } from '../types';
import Avatar from './Avatar';
import { formatDistanceToNow } from 'date-fns';
import { useChat } from '../contexts/ChatContext';
import { useAuth } from '../contexts/AuthContext';

interface UserListProps {
  users: User[];
  conversations: Conversation[];
}

const UserList: React.FC<UserListProps> = ({ users, conversations }) => {
  const { currentUser } = useAuth();
  const { setActiveConversation, activeConversation } = useChat();
  
  // Filter out current user
  const filteredUsers = users.filter(user => currentUser && user.id !== currentUser.id);
  
  const handleSelectUser = (user: User) => {
    // Find existing conversation or create a new one
    if (!currentUser) return;
    
    const conversation = conversations.find(
      c => c.participants.includes(user.id) && c.participants.includes(currentUser.id)
    );
    
    if (conversation) {
      setActiveConversation(conversation.id);
    } else {
      // In a real app, you would create a new conversation here
      // For now, just select the user
      const newConversation: Conversation = {
        id: `temp-${user.id}`,
        participants: [currentUser.id, user.id],
        updatedAt: new Date().toISOString()
      };
      setActiveConversation(newConversation.id);
    }
  };
  
  // Sort users by online status and then by username
  const sortedUsers = [...filteredUsers].sort((a, b) => {
    if (a.isOnline && !b.isOnline) return -1;
    if (!a.isOnline && b.isOnline) return 1;
    return a.username.localeCompare(b.username);
  });

  return (
    <div className="h-full overflow-y-auto">
      <h2 className="text-purple-300 font-semibold px-4 py-2 text-xs uppercase tracking-wider">
        Users
      </h2>
      <ul className="space-y-1">
        {sortedUsers.map(user => {
          // Find conversation with this user if exists
          const conversation = conversations.find(
            c => currentUser && c.participants.includes(user.id) && c.participants.includes(currentUser.id)
          );
          
          const isActive = activeConversation && activeConversation.participants.includes(user.id);
          
          // Check if there are unread messages
          const hasUnread = conversation?.lastMessage && 
                            !conversation.lastMessage.read && 
                            currentUser && 
                            conversation.lastMessage.receiverId === currentUser.id;
          
          return (
            <li 
              key={user.id}
              onClick={() => handleSelectUser(user)}
              className={`px-4 py-2 flex items-center space-x-3 cursor-pointer transition-colors ${
                isActive 
                  ? 'bg-gray-800 hover:bg-gray-800' 
                  : 'hover:bg-gray-900'
              }`}
            >
              <Avatar 
                username={user.username} 
                colorClass={user.avatar}
                isOnline={user.isOnline}
              />
              <div className="flex-1 min-w-0">
                <div className="flex justify-between items-center">
                  <p className="font-medium truncate">{user.username}</p>
                  {hasUnread && (
                    <span className="inline-block w-2 h-2 bg-purple-500 rounded-full"></span>
                  )}
                </div>
                <p className="text-gray-400 text-sm truncate">
                  {user.isOnline 
                    ? 'Online' 
                    : user.lastSeen 
                      ? `Last seen ${formatDistanceToNow(new Date(user.lastSeen), { addSuffix: true })}` 
                      : 'Offline'
                  }
                </p>
              </div>
            </li>
          );
        })}
      </ul>
    </div>
  );
};

export default UserList;