import React, { useState } from 'react';
import { Send, Smile } from 'lucide-react';

interface MessageInputProps {
  onSendMessage: (content: string) => void;
  disabled?: boolean;
}

const MessageInput: React.FC<MessageInputProps> = ({ onSendMessage, disabled = false }) => {
  const [message, setMessage] = useState('');
  
  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    
    if (message.trim() && !disabled) {
      onSendMessage(message.trim());
      setMessage('');
    }
  };
  
  return (
    <form 
      onSubmit={handleSubmit} 
      className="border-t border-gray-800 p-4 bg-gray-900 backdrop-blur-sm bg-opacity-60"
    >
      <div className="flex items-center space-x-2">
        <button 
          type="button" 
          className="p-2 text-gray-400 hover:text-gray-200 transition-colors"
          title="Insert emoji (not implemented)"
        >
          <Smile size={20} />
        </button>
        
        <div className="flex-1 relative">
          <input
            type="text"
            value={message}
            onChange={e => setMessage(e.target.value)}
            placeholder={disabled ? "Select a conversation to start chatting" : "Type a message..."}
            className="w-full bg-gray-850 text-white rounded-full py-2 px-4 focus:outline-none focus:ring-2 focus:ring-purple-500 placeholder-gray-500"
            disabled={disabled}
          />
        </div>
        
        <button 
          type="submit"
          disabled={!message.trim() || disabled}
          className={`p-2 rounded-full ${
            message.trim() && !disabled
              ? 'bg-purple-600 text-white hover:bg-purple-700'
              : 'bg-gray-800 text-gray-500 cursor-not-allowed'
          } transition-colors`}
        >
          <Send size={20} />
        </button>
      </div>
    </form>
  );
};

export default MessageInput;