import React, { useState } from 'react';
import { Wallet, CreditCard, ArrowRightLeft, History, LogOut, Menu, X, ArrowUpCircle, ArrowDownCircle } from 'lucide-react';
import { Toaster, toast } from 'react-hot-toast';

interface Transaction {
  id: string;
  type: 'deposit' | 'withdrawal' | 'transfer';
  amount: number;
  date: string;
  description: string;
}

interface Account {
  type: string;
  balance: number;
  number: string;
}

function App() {
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [activeTab, setActiveTab] = useState('dashboard');
  const [selectedAccount, setSelectedAccount] = useState<Account | null>(null);
  const [amount, setAmount] = useState('');
  const [showTransactionModal, setShowTransactionModal] = useState(false);
  const [transactionType, setTransactionType] = useState<'deposit' | 'withdrawal' | null>(null);

  const [accounts, setAccounts] = useState<Account[]>([
    { type: 'Checking', balance: 125000.00, number: '**** 1234' },
    { type: 'Savings', balance: 450000.00, number: '**** 5678' },
    { type: 'Credit', balance: -25000.00, number: '**** 9012' }
  ]);

  const [transactions, setTransactions] = useState<Transaction[]>([
    {
      id: '1',
      type: 'deposit',
      amount: 75000,
      date: '2025-05-23',
      description: 'Salary deposit'
    },
    {
      id: '2',
      type: 'withdrawal',
      amount: -2500,
      date: '2025-05-22',
      description: 'ATM withdrawal'
    },
    {
      id: '3',
      type: 'transfer',
      amount: -15000,
      date: '2025-05-21',
      description: 'Transfer to savings'
    }
  ]);

  const formatCurrency = (amount: number) => {
    return new Intl.NumberFormat('en-IN', {
      style: 'currency',
      currency: 'INR',
      minimumFractionDigits: 2
    }).format(amount);
  };

  const handleTransaction = (type: 'deposit' | 'withdrawal') => {
    setTransactionType(type);
    setShowTransactionModal(true);
  };

  const processTransaction = () => {
    if (!selectedAccount || !amount || !transactionType) {
      toast.error('Please fill in all fields');
      return;
    }

    const numAmount = parseFloat(amount);
    if (isNaN(numAmount) || numAmount <= 0) {
      toast.error('Please enter a valid amount');
      return;
    }

    if (transactionType === 'withdrawal' && numAmount > selectedAccount.balance) {
      toast.error('Insufficient funds');
      return;
    }

    // Update account balance
    const updatedAccounts = accounts.map(account => {
      if (account.number === selectedAccount.number) {
        return {
          ...account,
          balance: transactionType === 'deposit' 
            ? account.balance + numAmount 
            : account.balance - numAmount
        };
      }
      return account;
    });

    // Add new transaction
    const newTransaction: Transaction = {
      id: Date.now().toString(),
      type: transactionType,
      amount: transactionType === 'deposit' ? numAmount : -numAmount,
      date: new Date().toISOString().split('T')[0],
      description: `${transactionType.charAt(0).toUpperCase() + transactionType.slice(1)} transaction`
    };

    setAccounts(updatedAccounts);
    setTransactions([newTransaction, ...transactions]);
    setShowTransactionModal(false);
    setAmount('');
    setSelectedAccount(null);
    toast.success(`${transactionType.charAt(0).toUpperCase() + transactionType.slice(1)} successful`);
  };

  return (
    <div className="min-h-screen bg-dark-900">
      <Toaster position="top-right" />
      
      {/* Navigation */}
      <nav className="glass-card fixed w-full z-10">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8">
          <div className="flex items-center justify-between h-16">
            <div className="flex items-center">
              <Wallet className="h-8 w-8 text-accent-500" />
              <span className="ml-2 text-xl font-bold">SecureBank</span>
            </div>

            {/* Desktop Navigation */}
            <div className="hidden md:block">
              <div className="flex items-center space-x-4">
                <button onClick={() => setActiveTab('dashboard')} 
                  className={`nav-link ${activeTab === 'dashboard' ? 'text-white' : ''}`}>
                  Dashboard
                </button>
                <button onClick={() => setActiveTab('transactions')}
                  className={`nav-link ${activeTab === 'transactions' ? 'text-white' : ''}`}>
                  Transactions
                </button>
                <button className="btn-primary flex items-center">
                  <LogOut className="h-4 w-4 mr-2" />
                  Logout
                </button>
              </div>
            </div>

            {/* Mobile menu button */}
            <div className="md:hidden">
              <button onClick={() => setIsMenuOpen(!isMenuOpen)} className="text-gray-300 hover:text-white">
                {isMenuOpen ? <X className="h-6 w-6" /> : <Menu className="h-6 w-6" />}
              </button>
            </div>
          </div>
        </div>

        {/* Mobile Navigation */}
        {isMenuOpen && (
          <div className="md:hidden">
            <div className="px-2 pt-2 pb-3 space-y-1 sm:px-3">
              <button onClick={() => {
                setActiveTab('dashboard');
                setIsMenuOpen(false);
              }} className="block w-full text-left px-3 py-2 nav-link">
                Dashboard
              </button>
              <button onClick={() => {
                setActiveTab('transactions');
                setIsMenuOpen(false);
              }} className="block w-full text-left px-3 py-2 nav-link">
                Transactions
              </button>
              <button className="block w-full text-left px-3 py-2 text-accent-500 hover:text-accent-400">
                Logout
              </button>
            </div>
          </div>
        )}
      </nav>

      {/* Main Content */}
      <main className="pt-20 pb-8 px-4 sm:px-6 lg:px-8 max-w-7xl mx-auto">
        {activeTab === 'dashboard' ? (
          <>
            {/* Account Cards */}
            <div className="grid md:grid-cols-3 gap-6 mb-8">
              {accounts.map((account, index) => (
                <div key={index} className="glass-card p-6">
                  <div className="flex items-center justify-between mb-4">
                    <div className="flex items-center">
                      <CreditCard className="h-6 w-6 text-accent-500" />
                      <h3 className="ml-2 font-semibold">{account.type}</h3>
                    </div>
                    <span className="text-sm text-gray-400">{account.number}</span>
                  </div>
                  <p className="text-2xl font-bold">
                    {formatCurrency(account.balance)}
                  </p>
                </div>
              ))}
            </div>

            {/* Quick Actions */}
            <div className="glass-card p-6 mb-8">
              <h2 className="text-xl font-semibold mb-4">Quick Actions</h2>
              <div className="grid grid-cols-2 md:grid-cols-4 gap-4">
                <button 
                  onClick={() => handleTransaction('deposit')}
                  className="btn-secondary flex items-center justify-center">
                  <ArrowUpCircle className="h-4 w-4 mr-2" />
                  Deposit
                </button>
                <button 
                  onClick={() => handleTransaction('withdrawal')}
                  className="btn-secondary flex items-center justify-center">
                  <ArrowDownCircle className="h-4 w-4 mr-2" />
                  Withdraw
                </button>
                <button className="btn-secondary flex items-center justify-center">
                  <ArrowRightLeft className="h-4 w-4 mr-2" />
                  Transfer
                </button>
                <button className="btn-secondary flex items-center justify-center">
                  <History className="h-4 w-4 mr-2" />
                  History
                </button>
              </div>
            </div>
          </>
        ) : (
          <div className="glass-card p-6">
            <h2 className="text-xl font-semibold mb-6">Recent Transactions</h2>
            <div className="space-y-4">
              {transactions.map(transaction => (
                <div key={transaction.id} 
                  className="flex items-center justify-between p-4 bg-dark-700 rounded-lg">
                  <div>
                    <p className="font-medium">{transaction.description}</p>
                    <p className="text-sm text-gray-400">{transaction.date}</p>
                  </div>
                  <span className={`font-semibold ${
                    transaction.type === 'deposit' 
                      ? 'text-green-500' 
                      : 'text-red-500'
                  }`}>
                    {transaction.amount > 0 ? '+' : ''}
                    {formatCurrency(transaction.amount)}
                  </span>
                </div>
              ))}
            </div>
          </div>
        )}
      </main>

      {/* Transaction Modal */}
      {showTransactionModal && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50">
          <div className="glass-card p-6 max-w-md w-full mx-4">
            <h2 className="text-xl font-semibold mb-4">
              {transactionType === 'deposit' ? 'Make a Deposit' : 'Make a Withdrawal'}
            </h2>
            
            <div className="space-y-4">
              <div>
                <label className="block text-sm font-medium mb-1">Select Account</label>
                <select 
                  className="input-field w-full"
                  value={selectedAccount?.number || ''}
                  onChange={(e) => setSelectedAccount(accounts.find(a => a.number === e.target.value) || null)}
                >
                  <option value="">Select an account</option>
                  {accounts.map(account => (
                    <option key={account.number} value={account.number}>
                      {account.type} - {account.number}
                    </option>
                  ))}
                </select>
              </div>

              <div>
                <label className="block text-sm font-medium mb-1">Amount (₹)</label>
                <input
                  type="number"
                  className="input-field w-full"
                  value={amount}
                  onChange={(e) => setAmount(e.target.value)}
                  min="0"
                  step="0.01"
                />
              </div>

              <div className="flex space-x-3">
                <button
                  onClick={processTransaction}
                  className="btn-primary flex-1"
                >
                  Confirm
                </button>
                <button
                  onClick={() => {
                    setShowTransactionModal(false);
                    setAmount('');
                    setSelectedAccount(null);
                  }}
                  className="btn-secondary flex-1"
                >
                  Cancel
                </button>
              </div>
            </div>
          </div>
        </div>
      )}
    </div>
  );
}

export default App;