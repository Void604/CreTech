import React, { useEffect, useRef } from 'react';
import { Message, User } from '../types';
import { format } from 'date-fns';
import Avatar from './Avatar';
import { useAuth } from '../contexts/AuthContext';

interface MessageListProps {
  messages: Message[];
  getUser: (userId: string) => User | undefined;
}

const MessageList: React.FC<MessageListProps> = ({ messages, getUser }) => {
  const { currentUser } = useAuth();
  const messagesEndRef = useRef<HTMLDivElement>(null);
  
  // Scroll to bottom when messages change
  useEffect(() => {
    messagesEndRef.current?.scrollIntoView({ behavior: 'smooth' });
  }, [messages]);
  
  if (messages.length === 0) {
    return (
      <div className="flex-1 flex items-center justify-center">
        <p className="text-gray-500 italic">No messages yet. Start the conversation!</p>
      </div>
    );
  }

  // Group messages by date
  const groupedMessages: { [date: string]: Message[] } = {};
  messages.forEach(message => {
    const date = new Date(message.timestamp).toISOString().split('T')[0];
    if (!groupedMessages[date]) {
      groupedMessages[date] = [];
    }
    groupedMessages[date].push(message);
  });

  return (
    <div className="flex-1 overflow-y-auto p-4">
      {Object.entries(groupedMessages).map(([date, dateMessages]) => (
        <div key={date}>
          <div className="flex justify-center my-4">
            <span className="text-xs px-2 py-1 bg-gray-800 rounded-full text-gray-400">
              {format(new Date(date), 'MMMM d, yyyy')}
            </span>
          </div>
          
          {dateMessages.map((message, index) => {
            const isCurrentUser = currentUser && message.senderId === currentUser.id;
            const sender = getUser(message.senderId);
            const consecutive = 
              index > 0 && 
              dateMessages[index - 1].senderId === message.senderId;
            
            if (!sender) return null;
            
            return (
              <div key={message.id} className={`mb-4 flex ${isCurrentUser ? 'justify-end' : 'justify-start'}`}>
                <div className={`flex ${isCurrentUser ? 'flex-row-reverse' : 'flex-row'} max-w-[80%]`}>
                  {!consecutive && !isCurrentUser && (
                    <div className="flex-shrink-0 mr-2 self-end mb-1">
                      <Avatar 
                        username={sender.username} 
                        colorClass={sender.avatar}
                        size="sm"
                        showStatus={false}
                      />
                    </div>
                  )}
                  
                  <div className={`${consecutive && !isCurrentUser ? 'ml-8' : ''}`}>
                    {!consecutive && !isCurrentUser && (
                      <p className="text-xs text-gray-400 mb-1 ml-1">{sender.username}</p>
                    )}
                    
                    <div className={`${
                      isCurrentUser 
                        ? 'bg-purple-600 text-white rounded-tl-lg rounded-tr-lg rounded-bl-lg' 
                        : 'bg-gray-800 text-white rounded-tl-lg rounded-tr-lg rounded-br-lg'
                    } px-4 py-2 shadow-sm`}>
                      <p>{message.content}</p>
                      <p className="text-xs mt-1 opacity-70">
                        {format(new Date(message.timestamp), 'h:mm a')}
                        {isCurrentUser && message.read && (
                          <span className="ml-2">✓</span>
                        )}
                      </p>
                    </div>
                  </div>
                </div>
              </div>
            );
          })}
        </div>
      ))}
      <div ref={messagesEndRef} />
    </div>
  );
};

export default MessageList;