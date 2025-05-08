import React, { createContext, useState, useEffect, useContext } from 'react';
import { v4 as uuidv4 } from 'uuid';
import { Message, User, Conversation, ChatRoom } from '../types';
import { useAuth } from './AuthContext';

interface ChatContextType {
  messages: Message[];
  conversations: Conversation[];
  chatRooms: ChatRoom[];
  activeConversation: Conversation | null;
  activeChatRoom: ChatRoom | null;
  users: User[];
  sendMessage: (content: string, roomId?: string, receiverId?: string) => Promise<void>;
  setActiveConversation: (conversationId: string | null) => void;
  setActiveChatRoom: (roomId: string | null) => void;
  markAsRead: (messageIds: string[]) => void;
  getConversationMessages: (conversationId: string) => Message[];
  getChatRoomMessages: (roomId: string) => Message[];
  getUser: (userId: string) => User | undefined;
  createChatRoom: (name: string, description: string) => Promise<void>;
  joinChatRoom: (roomId: string) => Promise<void>;
  leaveChatRoom: (roomId: string) => Promise<void>;
}

const ChatContext = createContext<ChatContextType | undefined>(undefined);

export const useChat = () => {
  const context = useContext(ChatContext);
  if (context === undefined) {
    throw new Error('useChat must be used within a ChatProvider');
  }
  return context;
};

export const ChatProvider: React.FC<{ children: React.ReactNode }> = ({ children }) => {
  const { currentUser } = useAuth();
  const [messages, setMessages] = useState<Message[]>([]);
  const [conversations, setConversations] = useState<Conversation[]>([]);
  const [chatRooms, setChatRooms] = useState<ChatRoom[]>([]);
  const [activeConversation, setActiveConversationState] = useState<Conversation | null>(null);
  const [activeChatRoom, setActiveChatRoomState] = useState<ChatRoom | null>(null);
  const [users, setUsers] = useState<User[]>([]);

  // Load initial data
  useEffect(() => {
    const storedUsers = localStorage.getItem('chatUsers');
    const storedRooms = localStorage.getItem('chatRooms');
    
    if (storedUsers) {
      setUsers(JSON.parse(storedUsers));
    } else {
      const mockUsers: User[] = [
        {
          id: uuidv4(),
          username: 'sarah_design',
          email: 'sarah@example.com',
          avatar: 'bg-purple-500',
          isOnline: Math.random() > 0.5,
          lastSeen: new Date().toISOString()
        },
        {
          id: uuidv4(),
          username: 'alex_dev',
          email: 'alex@example.com',
          avatar: 'bg-blue-500',
          isOnline: Math.random() > 0.5,
          lastSeen: new Date().toISOString()
        }
      ];
      localStorage.setItem('chatUsers', JSON.stringify(mockUsers));
      setUsers(mockUsers);
    }

    if (storedRooms) {
      setChatRooms(JSON.parse(storedRooms));
    } else {
      const mockRooms: ChatRoom[] = [
        {
          id: uuidv4(),
          name: 'General Chat',
          description: 'A place for general discussions',
          createdBy: 'system',
          createdAt: new Date().toISOString(),
          participants: [],
          avatar: 'bg-green-500'
        },
        {
          id: uuidv4(),
          name: 'Tech Talk',
          description: 'Discuss the latest in technology',
          createdBy: 'system',
          createdAt: new Date().toISOString(),
          participants: [],
          avatar: 'bg-blue-500'
        }
      ];
      localStorage.setItem('chatRooms', JSON.stringify(mockRooms));
      setChatRooms(mockRooms);
    }

    const storedMessages = localStorage.getItem('chatMessages');
    if (storedMessages) {
      setMessages(JSON.parse(storedMessages));
    }
    
    const storedConversations = localStorage.getItem('chatConversations');
    if (storedConversations) {
      setConversations(JSON.parse(storedConversations));
    }
  }, []);

  const getUser = (userId: string): User | undefined => {
    return users.find(user => user.id === userId);
  };

  const setActiveConversation = (conversationId: string | null) => {
    setActiveChatRoomState(null);
    if (!conversationId) {
      setActiveConversationState(null);
      return;
    }
    
    const conversation = conversations.find(c => c.id === conversationId);
    if (conversation) {
      setActiveConversationState(conversation);
      
      if (currentUser) {
        const conversationMessages = messages.filter(
          m => conversation.participants.includes(m.senderId) && 
               conversation.participants.includes(m.receiverId!) &&
               m.receiverId === currentUser.id &&
               !m.read
        );
        
        if (conversationMessages.length > 0) {
          markAsRead(conversationMessages.map(m => m.id));
        }
      }
    }
  };

  const setActiveChatRoom = (roomId: string | null) => {
    setActiveConversationState(null);
    if (!roomId) {
      setActiveChatRoomState(null);
      return;
    }
    
    const room = chatRooms.find(r => r.id === roomId);
    if (room) {
      setActiveChatRoomState(room);
    }
  };

  const getConversationMessages = (conversationId: string): Message[] => {
    const conversation = conversations.find(c => c.id === conversationId);
    if (!conversation) return [];
    
    return messages.filter(
      message => 
        conversation.participants.includes(message.senderId) && 
        conversation.participants.includes(message.receiverId!)
    ).sort((a, b) => new Date(a.timestamp).getTime() - new Date(b.timestamp).getTime());
  };

  const getChatRoomMessages = (roomId: string): Message[] => {
    return messages
      .filter(message => message.roomId === roomId)
      .sort((a, b) => new Date(a.timestamp).getTime() - new Date(b.timestamp).getTime());
  };

  const sendMessage = async (content: string, roomId?: string, receiverId?: string): Promise<void> => {
    if (!currentUser) return;
    
    const timestamp = new Date().toISOString();
    const newMessage: Message = {
      id: uuidv4(),
      senderId: currentUser.id,
      receiverId,
      roomId,
      content,
      timestamp,
      read: false
    };
    
    const updatedMessages = [...messages, newMessage];
    setMessages(updatedMessages);
    localStorage.setItem('chatMessages', JSON.stringify(updatedMessages));
    
    if (receiverId) {
      // Handle direct message
      let conversation = conversations.find(
        c => c.participants.includes(currentUser.id) && 
             c.participants.includes(receiverId)
      );
      
      if (!conversation) {
        conversation = {
          id: uuidv4(),
          participants: [currentUser.id, receiverId],
          lastMessage: newMessage,
          updatedAt: timestamp
        };
        
        const updatedConversations = [...conversations, conversation];
        setConversations(updatedConversations);
        localStorage.setItem('chatConversations', JSON.stringify(updatedConversations));
      } else {
        const updatedConversations = conversations.map(c => 
          c.id === conversation!.id 
            ? { ...c, lastMessage: newMessage, updatedAt: timestamp } 
            : c
        );
        
        setConversations(updatedConversations);
        localStorage.setItem('chatConversations', JSON.stringify(updatedConversations));
      }
    }
  };

  const markAsRead = (messageIds: string[]): void => {
    if (messageIds.length === 0) return;
    
    const updatedMessages = messages.map(message => 
      messageIds.includes(message.id) 
        ? { ...message, read: true } 
        : message
    );
    
    setMessages(updatedMessages);
    localStorage.setItem('chatMessages', JSON.stringify(updatedMessages));
    
    const updatedConversations = conversations.map(conversation => {
      if (conversation.lastMessage && messageIds.includes(conversation.lastMessage.id)) {
        return {
          ...conversation,
          lastMessage: { ...conversation.lastMessage, read: true }
        };
      }
      return conversation;
    });
    
    setConversations(updatedConversations);
    localStorage.setItem('chatConversations', JSON.stringify(updatedConversations));
  };

  const createChatRoom = async (name: string, description: string): Promise<void> => {
    if (!currentUser) return;

    const newRoom: ChatRoom = {
      id: uuidv4(),
      name,
      description,
      createdBy: currentUser.id,
      createdAt: new Date().toISOString(),
      participants: [currentUser.id],
      avatar: `bg-${['purple', 'blue', 'green', 'red'][Math.floor(Math.random() * 4)]}-500`
    };

    const updatedRooms = [...chatRooms, newRoom];
    setChatRooms(updatedRooms);
    localStorage.setItem('chatRooms', JSON.stringify(updatedRooms));
  };

  const joinChatRoom = async (roomId: string): Promise<void> => {
    if (!currentUser) return;

    const updatedRooms = chatRooms.map(room => 
      room.id === roomId && !room.participants.includes(currentUser.id)
        ? { ...room, participants: [...room.participants, currentUser.id] }
        : room
    );

    setChatRooms(updatedRooms);
    localStorage.setItem('chatRooms', JSON.stringify(updatedRooms));
  };

  const leaveChatRoom = async (roomId: string): Promise<void> => {
    if (!currentUser) return;

    const updatedRooms = chatRooms.map(room => 
      room.id === roomId
        ? { ...room, participants: room.participants.filter(id => id !== currentUser.id) }
        : room
    );

    setChatRooms(updatedRooms);
    localStorage.setItem('chatRooms', JSON.stringify(updatedRooms));

    if (activeChatRoom?.id === roomId) {
      setActiveChatRoom(null);
    }
  };

  return (
    <ChatContext.Provider value={{
      messages,
      conversations,
      chatRooms,
      activeConversation,
      activeChatRoom,
      users,
      sendMessage,
      setActiveConversation,
      setActiveChatRoom,
      markAsRead,
      getConversationMessages,
      getChatRoomMessages,
      getUser,
      createChatRoom,
      joinChatRoom,
      leaveChatRoom
    }}>
      {children}
    </ChatContext.Provider>
  );
};