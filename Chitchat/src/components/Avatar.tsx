import React from 'react';

interface AvatarProps {
  username: string;
  colorClass: string;
  size?: 'sm' | 'md' | 'lg';
  isOnline?: boolean;
  showStatus?: boolean;
}

const Avatar: React.FC<AvatarProps> = ({ 
  username, 
  colorClass, 
  size = 'md', 
  isOnline = false,
  showStatus = true
}) => {
  const sizeClasses = {
    sm: 'w-8 h-8 text-xs',
    md: 'w-10 h-10 text-sm',
    lg: 'w-12 h-12 text-base'
  };
  
  const initials = username
    .split(' ')
    .map(name => name[0])
    .join('')
    .toUpperCase()
    .substring(0, 2);
  
  return (
    <div className="relative">
      <div className={`${sizeClasses[size]} ${colorClass} rounded-full flex items-center justify-center text-white font-medium shadow-md`}>
        {initials}
      </div>
      {showStatus && (
        <div className={`absolute bottom-0 right-0 w-3 h-3 rounded-full border-2 border-gray-950 ${isOnline ? 'bg-green-500' : 'bg-gray-400'}`}></div>
      )}
    </div>
  );
};

export default Avatar;