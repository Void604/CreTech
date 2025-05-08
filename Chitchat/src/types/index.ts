export interface User {
  id: string;
  username: string;
  email: string;
  avatar: string;
  isOnline: boolean;
  lastSeen?: string;
}

export interface Message {
  id: string;
  senderId: string;
  receiverId?: string;
  roomId?: string;
  content: string;
  timestamp: string;
  read: boolean;
}

export interface Conversation {
  id: string;
  participants: string[];
  lastMessage?: Message;
  updatedAt: string;
}

export interface ChatRoom {
  id: string;
  name: string;
  description: string;
  createdBy: string;
  createdAt: string;
  participants: string[];
  avatar: string;
}