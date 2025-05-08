import React, { useEffect } from 'react';
import { useChat } from '../contexts/ChatContext';
import { useAuth } from '../contexts/AuthContext';
import UserList from '../components/UserList';
import ChatRoomList from '../components/ChatRoomList';
import MessageList from '../components/MessageList';
import MessageInput from '../components/MessageInput';
import Avatar from '../components/Avatar';
import { LogOut, Menu, X, Users } from 'lucide-react';

const Chat: React.FC = () => {
  const { currentUser, logout } = useAuth();
  const { 
    users, 
    conversations,
    chatRooms,
    activeConversation,
    activeChatRoom,
    sendMessage,
    getConversationMessages,
    getChatRoomMessages,
    getUser,
    setActiveConversation
  } = useChat();
  
  const [menuOpen, setMenuOpen] = React.useState(false);
  const [showRooms, setShowRooms] = React.useState(true);
  
  useEffect(() => {
    if (!activeConversation && !activeChatRoom && conversations.length > 0 && window.innerWidth < 768) {
      setActiveConversation(conversations[0].id);
    }
  }, [conversations, activeConversation, activeChatRoom, setActiveConversation]);
  
  const handleSendMessage = (content: string) => {
    if (!currentUser) return;
    
    if (activeChatRoom) {
      sendMessage(content, activeChatRoom.id);
    } else if (activeConversation) {
      const otherParticipantId = activeConversation.participants.find(
        id => id !== currentUser.id
      );
      
      if (otherParticipantId) {
        sendMessage(content, undefined, otherParticipantId);
      }
    }
  };
  
  const messages = activeChatRoom
    ? getChatRoomMessages(activeChatRoom.id)
    : activeConversation
    ? getConversationMessages(activeConversation.id)
    : [];
  
  const conversationPartner = activeConversation && currentUser 
    ? getUser(activeConversation.participants.find(id => id !== currentUser.id) || '')
    : undefined;
  
  return (
    <div className="h-screen flex flex-col">
      <header className="bg-gray-900 shadow-md p-4 flex justify-between items-center">
        <div className="flex items-center">
          <button 
            className="md:hidden mr-3 text-gray-400"
            onClick={() => setMenuOpen(!menuOpen)}
          >
            <Menu size={20} />
          </button>
          <h1 className="text-xl font-bold bg-gradient-to-r from-purple-400 to-blue-500 bg-clip-text text-transparent">
            ChatApp
          </h1>
        </div>
        {currentUser && (
          <div className="flex items-center space-x-3">
            <div className="hidden md:block text-right">
              <p className="text-sm font-medium">{currentUser.username}</p>
              <p className="text-xs text-gray-400">{currentUser.email}</p>
            </div>
            <Avatar
              username={currentUser.username}
              colorClass={currentUser.avatar}
              isOnline={true}
            />
            <button 
              onClick={logout}
              className="p-2 rounded-full hover:bg-gray-800 text-gray-400 hover:text-gray-200 transition-colors"
              title="Logout"
            >
              <LogOut size={18} />
            </button>
          </div>
        )}
      </header>
      
      <div className="flex-1 flex overflow-hidden">
        <aside className={`bg-gray-900 w-full md:w-80 md:block flex-shrink-0 ${menuOpen ? 'block fixed inset-0 z-10' : 'hidden'}`}>
          <div className="flex flex-col h-full">
            {menuOpen && (
              <div className="p-4 flex justify-between items-center md:hidden">
                <h2 className="font-bold">Chat</h2>
                <button 
                  onClick={() => setMenuOpen(false)}
                  className="p-2 text-gray-400"
                >
                  <X size={20} />
                </button>
              </div>
            )}
            <div className="flex space-x-2 px-4 mb-2">
              <button
                onClick={() => setShowRooms(false)}
                className={`flex-1 py-2 px-4 rounded-lg text-sm font-medium transition-colors ${
                  !showRooms
                    ? 'bg-purple-600 text-white'
                    : 'text-gray-400 hover:text-white'
                }`}
              >
                Direct
              </button>
              <button
                onClick={() => setShowRooms(true)}
                className={`flex-1 py-2 px-4 rounded-lg text-sm font-medium transition-colors ${
                  showRooms
                    ? 'bg-purple-600 text-white'
                    : 'text-gray-400 hover:text-white'
                }`}
              >
                Rooms
              </button>
            </div>
            {showRooms ? (
              <ChatRoomList rooms={chatRooms} />
            ) : (
              <UserList users={users} conversations={conversations} />
            )}
          </div>
        </aside>
        
        <main className="flex-1 flex flex-col bg-gray-950">
          {(activeConversation && conversationPartner) || activeChatRoom ? (
            <>
              <div className="p-4 border-b border-gray-800 flex items-center">
                <Avatar 
                  username={activeChatRoom ? activeChatRoom.name : conversationPartner!.username}
                  colorClass={activeChatRoom ? activeChatRoom.avatar : conversationPartner!.avatar}
                  isOnline={activeChatRoom ? true : conversationPartner!.isOnline}
                />
                <div className="ml-3 flex-1">
                  <div className="flex items-center justify-between">
                    <h2 className="font-medium">
                      {activeChatRoom ? activeChatRoom.name : conversationPartner!.username}
                    </h2>
                    {activeChatRoom && (
                      <div className="flex items-center text-gray-400">
                        <Users size={16} className="mr-1" />
                        <span className="text-sm">{activeChatRoom.participants.length}</span>
                      </div>
                    )}
                  </div>
                  <p className="text-xs text-gray-400">
                    {activeChatRoom 
                      ? activeChatRoom.description
                      : conversationPartner!.isOnline ? 'Online' : 'Offline'
                    }
                  </p>
                </div>
              </div>
              
              <MessageList messages={messages} getUser={getUser} />
              
              <MessageInput onSendMessage={handleSendMessage} />
            </>
          ) : (
            <div className="flex-1 flex items-center justify-center p-4 text-center">
              <div>
                <p className="text-gray-400 mb-2">
                  {showRooms 
                    ? "Select a chat room to start messaging"
                    : "Select a conversation to start chatting"
                  }
                </p>
                <p className="text-sm text-gray-500">
                  {showRooms
                    ? chatRooms.length === 0 
                      ? "No chat rooms available"
                      : "Choose a room from the sidebar"
                    : conversations.length === 0 
                      ? "You don't have any conversations yet"
                      : "Choose a person from the sidebar"
                  }
                </p>
                <button 
                  onClick={() => setMenuOpen(true)}
                  className="mt-4 px-4 py-2 bg-purple-600 text-white rounded-full text-sm hover:bg-purple-700 transition-colors md:hidden"
                >
                  Open Chat
                </button>
              </div>
            </div>
          )}
        </main>
      </div>
    </div>
  );
};

export default Chat;