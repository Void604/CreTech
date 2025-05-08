import React from 'react';
import { ChatRoom } from '../types';
import Avatar from './Avatar';
import { useChat } from '../contexts/ChatContext';
import { useAuth } from '../contexts/AuthContext';
import { Users, Plus } from 'lucide-react';

interface ChatRoomListProps {
  rooms: ChatRoom[];
}

const ChatRoomList: React.FC<ChatRoomListProps> = ({ rooms }) => {
  const { currentUser } = useAuth();
  const { 
    setActiveChatRoom, 
    activeChatRoom,
    joinChatRoom,
    leaveChatRoom
  } = useChat();
  const [showCreateModal, setShowCreateModal] = React.useState(false);
  const [newRoomName, setNewRoomName] = React.useState('');
  const [newRoomDescription, setNewRoomDescription] = React.useState('');
  
  const handleSelectRoom = (room: ChatRoom) => {
    setActiveChatRoom(room.id);
  };

  const handleJoinRoom = async (e: React.MouseEvent, roomId: string) => {
    e.stopPropagation();
    await joinChatRoom(roomId);
  };

  const handleLeaveRoom = async (e: React.MouseEvent, roomId: string) => {
    e.stopPropagation();
    await leaveChatRoom(roomId);
  };

  const handleCreateRoom = async (e: React.FormEvent) => {
    e.preventDefault();
    if (newRoomName.trim()) {
      await createChatRoom(newRoomName.trim(), newRoomDescription.trim());
      setNewRoomName('');
      setNewRoomDescription('');
      setShowCreateModal(false);
    }
  };

  return (
    <div className="h-full overflow-y-auto">
      <div className="flex justify-between items-center px-4 py-2">
        <h2 className="text-purple-300 font-semibold text-xs uppercase tracking-wider">
          Chat Rooms
        </h2>
        <button
          onClick={() => setShowCreateModal(true)}
          className="p-1 hover:bg-gray-800 rounded-full transition-colors"
          title="Create new room"
        >
          <Plus size={16} className="text-purple-300" />
        </button>
      </div>

      <ul className="space-y-1">
        {rooms.map(room => {
          const isActive = activeChatRoom?.id === room.id;
          const isParticipant = currentUser && room.participants.includes(currentUser.id);
          
          return (
            <li 
              key={room.id}
              onClick={() => handleSelectRoom(room)}
              className={`px-4 py-2 flex items-center space-x-3 cursor-pointer transition-colors ${
                isActive 
                  ? 'bg-gray-800 hover:bg-gray-800' 
                  : 'hover:bg-gray-900'
              }`}
            >
              <Avatar 
                username={room.name}
                colorClass={room.avatar}
                showStatus={false}
              />
              <div className="flex-1 min-w-0">
                <div className="flex justify-between items-center">
                  <p className="font-medium truncate">{room.name}</p>
                  <div className="flex items-center space-x-1">
                    <span className="text-xs text-gray-400">
                      <Users size={14} className="inline mr-1" />
                      {room.participants.length}
                    </span>
                    {currentUser && (
                      isParticipant ? (
                        <button
                          onClick={(e) => handleLeaveRoom(e, room.id)}
                          className="text-xs text-red-400 hover:text-red-300 ml-2"
                        >
                          Leave
                        </button>
                      ) : (
                        <button
                          onClick={(e) => handleJoinRoom(e, room.id)}
                          className="text-xs text-purple-400 hover:text-purple-300 ml-2"
                        >
                          Join
                        </button>
                      )
                    )}
                  </div>
                </div>
                <p className="text-gray-400 text-sm truncate">
                  {room.description}
                </p>
              </div>
            </li>
          );
        })}
      </ul>

      {showCreateModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center p-4">
          <div className="bg-gray-800 rounded-lg p-6 w-full max-w-md">
            <h3 className="text-lg font-semibold mb-4">Create New Chat Room</h3>
            <form onSubmit={handleCreateRoom}>
              <div className="mb-4">
                <label className="block text-sm font-medium text-gray-300 mb-1">
                  Room Name
                </label>
                <input
                  type="text"
                  value={newRoomName}
                  onChange={(e) => setNewRoomName(e.target.value)}
                  className="w-full px-3 py-2 bg-gray-700 rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500"
                  placeholder="Enter room name"
                  required
                />
              </div>
              <div className="mb-4">
                <label className="block text-sm font-medium text-gray-300 mb-1">
                  Description
                </label>
                <input
                  type="text"
                  value={newRoomDescription}
                  onChange={(e) => setNewRoomDescription(e.target.value)}
                  className="w-full px-3 py-2 bg-gray-700 rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500"
                  placeholder="Enter room description"
                />
              </div>
              <div className="flex justify-end space-x-3">
                <button
                  type="button"
                  onClick={() => setShowCreateModal(false)}
                  className="px-4 py-2 text-gray-300 hover:text-white transition-colors"
                >
                  Cancel
                </button>
                <button
                  type="submit"
                  className="px-4 py-2 bg-purple-600 text-white rounded-lg hover:bg-purple-700 transition-colors"
                >
                  Create Room
                </button>
              </div>
            </form>
          </div>
        </div>
      )}
    </div>
  );
};

export default ChatRoomList;