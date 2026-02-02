#!/usr/bin/env node

/**
 * Transform World - Knowledge Sharing Platform
 * 
 * This application connects learners and educators globally,
 * democratizing access to knowledge and skills that can change lives.
 */

const http = require('http');
const url = require('url');

// In-memory storage for knowledge articles
const knowledgeBase = [
  {
    id: 1,
    title: "如何通过编程改变世界 (How to Change the World Through Programming)",
    content: "编程不仅仅是写代码，它是解决真实问题的工具。通过创建有意义的软件，我们可以：1) 提高教育可及性 2) 改善医疗保健 3) 保护环境 4) 促进社会公平",
    author: "系统",
    category: "技术教育",
    impact: "高"
  },
  {
    id: 2,
    title: "开源协作的力量 (The Power of Open Source Collaboration)",
    content: "开源软件已经改变了世界。Linux、Git、Python等工具让全球开发者能够协作创新。通过分享知识和代码，我们可以加速人类进步。",
    author: "系统",
    category: "开源文化",
    impact: "高"
  },
  {
    id: 3,
    title: "可持续发展与技术 (Sustainable Development and Technology)",
    content: "技术可以帮助我们应对气候变化、能源危机和资源短缺。通过智能算法、数据分析和自动化，我们可以创建更可持续的未来。",
    author: "系统",
    category: "可持续发展",
    impact: "关键"
  }
];

let nextId = 4;

// Constants
const MAX_BODY_SIZE = 1024 * 1024; // 1MB limit for request body
const IMPACT_LEVELS = {
  HIGH: '高',
  CRITICAL: '关键'
};

// Server configuration
const PORT = process.env.PORT || 3000;
const HOST = process.env.HOST || '0.0.0.0';

// Helper function to send JSON response
function sendJSON(res, statusCode, data) {
  res.writeHead(statusCode, { 'Content-Type': 'application/json; charset=utf-8' });
  res.end(JSON.stringify(data, null, 2));
}

// Helper function to send HTML response
function sendHTML(res, statusCode, html) {
  res.writeHead(statusCode, { 'Content-Type': 'text/html; charset=utf-8' });
  res.end(html);
}

// Main request handler
function handleRequest(req, res) {
  const parsedUrl = url.parse(req.url, true);
  const pathname = parsedUrl.pathname;
  const method = req.method;

  // Enable CORS
  res.setHeader('Access-Control-Allow-Origin', '*');
  res.setHeader('Access-Control-Allow-Methods', 'GET, POST, PUT, DELETE, OPTIONS');
  res.setHeader('Access-Control-Allow-Headers', 'Content-Type');

  if (method === 'OPTIONS') {
    res.writeHead(200);
    res.end();
    return;
  }

  // Routes
  if (pathname === '/' && method === 'GET') {
    // Home page with web interface
    const html = `
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Transform World - 改变世界的知识平台</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        .container {
            max-width: 1200px;
            margin: 0 auto;
            background: white;
            border-radius: 20px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            overflow: hidden;
        }
        header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 40px;
            text-align: center;
        }
        h1 { 
            font-size: 2.5em; 
            margin-bottom: 10px;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.2);
        }
        .subtitle {
            font-size: 1.2em;
            opacity: 0.9;
        }
        .stats {
            display: flex;
            justify-content: space-around;
            padding: 30px;
            background: #f8f9fa;
            border-bottom: 2px solid #e9ecef;
        }
        .stat {
            text-align: center;
        }
        .stat-number {
            font-size: 2em;
            font-weight: bold;
            color: #667eea;
        }
        .stat-label {
            color: #6c757d;
            margin-top: 5px;
        }
        .content {
            padding: 40px;
        }
        .mission {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
            padding: 30px;
            border-radius: 15px;
            margin-bottom: 30px;
        }
        .mission h2 {
            margin-bottom: 15px;
        }
        .articles {
            display: grid;
            gap: 20px;
            margin-top: 30px;
        }
        .article {
            border: 2px solid #e9ecef;
            border-radius: 10px;
            padding: 25px;
            transition: all 0.3s;
            background: white;
        }
        .article:hover {
            border-color: #667eea;
            box-shadow: 0 5px 15px rgba(102, 126, 234, 0.2);
            transform: translateY(-2px);
        }
        .article-title {
            font-size: 1.5em;
            color: #2c3e50;
            margin-bottom: 10px;
        }
        .article-meta {
            display: flex;
            gap: 15px;
            margin-bottom: 15px;
            font-size: 0.9em;
            color: #6c757d;
        }
        .article-content {
            line-height: 1.8;
            color: #495057;
        }
        .badge {
            display: inline-block;
            padding: 5px 15px;
            border-radius: 20px;
            font-size: 0.85em;
            font-weight: bold;
        }
        .badge-high {
            background: #d4edda;
            color: #155724;
        }
        .badge-critical {
            background: #f8d7da;
            color: #721c24;
        }
        .api-section {
            background: #f8f9fa;
            padding: 25px;
            border-radius: 10px;
            margin-top: 30px;
        }
        .api-section h3 {
            margin-bottom: 15px;
            color: #2c3e50;
        }
        .api-endpoint {
            background: white;
            padding: 15px;
            border-left: 4px solid #667eea;
            margin: 10px 0;
            font-family: 'Courier New', monospace;
            border-radius: 5px;
        }
        footer {
            background: #2c3e50;
            color: white;
            padding: 30px;
            text-align: center;
        }
        .cta-button {
            display: inline-block;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 15px 30px;
            border-radius: 25px;
            text-decoration: none;
            font-weight: bold;
            margin-top: 20px;
            transition: transform 0.2s;
        }
        .cta-button:hover {
            transform: scale(1.05);
        }
    </style>
</head>
<body>
    <div class="container">
        <header>
            <h1>🌍 Transform World</h1>
            <p class="subtitle">通过知识分享改变世界 | Change the World Through Knowledge Sharing</p>
        </header>
        
        <div class="stats">
            <div class="stat">
                <div class="stat-number">${knowledgeBase.length}</div>
                <div class="stat-label">知识文章</div>
            </div>
            <div class="stat">
                <div class="stat-number">∞</div>
                <div class="stat-label">学习者</div>
            </div>
            <div class="stat">
                <div class="stat-number">100%</div>
                <div class="stat-label">免费开放</div>
            </div>
        </div>
        
        <div class="content">
            <div class="mission">
                <h2>🎯 我们的使命 (Our Mission)</h2>
                <p>我们相信知识是改变世界最强大的工具。通过创建开放、包容的学习平台，我们致力于：</p>
                <ul style="margin-top: 15px; line-height: 2;">
                    <li>✨ 让每个人都能获得优质教育资源</li>
                    <li>🤝 连接全球的学习者和教育者</li>
                    <li>💡 激发创新思维和解决问题的能力</li>
                    <li>🌱 培养能够改变世界的下一代领导者</li>
                </ul>
            </div>
            
            <h2 style="margin-bottom: 20px; color: #2c3e50;">📚 精选知识文章</h2>
            <div class="articles">
                ${knowledgeBase.map(article => `
                <div class="article">
                    <div class="article-title">${article.title}</div>
                    <div class="article-meta">
                        <span>👤 ${article.author}</span>
                        <span>📁 ${article.category}</span>
                        <span class="badge ${article.impact === '高' ? 'badge-high' : 'badge-critical'}">
                            影响力: ${article.impact}
                        </span>
                    </div>
                    <div class="article-content">${article.content}</div>
                </div>
                `).join('')}
            </div>
            
            <div class="api-section">
                <h3>🔌 API 接口 (REST API Endpoints)</h3>
                <p style="margin-bottom: 15px;">开发者可以使用以下 API 来集成知识平台：</p>
                <div class="api-endpoint">GET /api/articles - 获取所有文章</div>
                <div class="api-endpoint">GET /api/articles/:id - 获取指定文章</div>
                <div class="api-endpoint">POST /api/articles - 创建新文章</div>
                <div class="api-endpoint">GET /api/stats - 获取平台统计信息</div>
            </div>
            
            <div style="text-align: center; margin-top: 40px;">
                <a href="/api/articles" class="cta-button">🚀 探索 API</a>
            </div>
        </div>
        
        <footer>
            <p>🌟 开源项目 | 用技术改变世界 | Open Source for a Better World</p>
            <p style="margin-top: 10px; opacity: 0.8;">让我们一起创造一个更美好的未来</p>
        </footer>
    </div>
</body>
</html>
    `;
    sendHTML(res, 200, html);
  }
  
  else if (pathname === '/api/articles' && method === 'GET') {
    // Get all articles
    sendJSON(res, 200, {
      success: true,
      count: knowledgeBase.length,
      data: knowledgeBase
    });
  }
  
  else if (pathname.match(/^\/api\/articles\/\d+$/) && method === 'GET') {
    // Get single article
    const id = parseInt(pathname.split('/')[3]);
    const article = knowledgeBase.find(a => a.id === id);
    
    if (article) {
      sendJSON(res, 200, { success: true, data: article });
    } else {
      sendJSON(res, 404, { success: false, error: 'Article not found' });
    }
  }
  
  else if (pathname === '/api/articles' && method === 'POST') {
    // Create new article
    let body = '';
    let bodySize = 0;
    
    req.on('data', chunk => {
      bodySize += chunk.length;
      
      // Prevent memory exhaustion attacks
      if (bodySize > MAX_BODY_SIZE) {
        res.writeHead(413, { 'Content-Type': 'application/json; charset=utf-8' });
        res.end(JSON.stringify({ success: false, error: 'Request body too large' }));
        req.destroy();
        return;
      }
      
      body += chunk.toString();
    });
    
    req.on('end', () => {
      try {
        const newArticle = JSON.parse(body);
        
        // Validate required fields
        if (!newArticle.title || typeof newArticle.title !== 'string' || newArticle.title.trim() === '') {
          sendJSON(res, 400, { success: false, error: 'Title is required and must be a non-empty string' });
          return;
        }
        if (!newArticle.content || typeof newArticle.content !== 'string' || newArticle.content.trim() === '') {
          sendJSON(res, 400, { success: false, error: 'Content is required and must be a non-empty string' });
          return;
        }
        if (!newArticle.category || typeof newArticle.category !== 'string' || newArticle.category.trim() === '') {
          sendJSON(res, 400, { success: false, error: 'Category is required and must be a non-empty string' });
          return;
        }
        if (!newArticle.impact || typeof newArticle.impact !== 'string' || newArticle.impact.trim() === '') {
          sendJSON(res, 400, { success: false, error: 'Impact is required and must be a non-empty string' });
          return;
        }
        
        // Sanitize inputs
        newArticle.title = newArticle.title.trim();
        newArticle.content = newArticle.content.trim();
        newArticle.category = newArticle.category.trim();
        newArticle.impact = newArticle.impact.trim();
        
        newArticle.id = nextId++;
        newArticle.author = newArticle.author?.trim() || '匿名';
        knowledgeBase.push(newArticle);
        sendJSON(res, 201, { 
          success: true, 
          message: 'Article created successfully',
          data: newArticle 
        });
      } catch (error) {
        sendJSON(res, 400, { success: false, error: 'Invalid JSON' });
      }
    });
  }
  
  else if (pathname === '/api/stats' && method === 'GET') {
    // Get platform statistics
    const stats = {
      totalArticles: knowledgeBase.length,
      categories: [...new Set(knowledgeBase.map(a => a.category))],
      highImpactArticles: knowledgeBase.filter(a => 
        a.impact === IMPACT_LEVELS.HIGH || a.impact === IMPACT_LEVELS.CRITICAL
      ).length,
      mission: "Democratizing knowledge to transform the world"
    };
    sendJSON(res, 200, { success: true, data: stats });
  }
  
  else {
    sendJSON(res, 404, { success: false, error: 'Endpoint not found' });
  }
}

// Create and start server
const server = http.createServer(handleRequest);

server.listen(PORT, HOST, () => {
  console.log('');
  console.log('═══════════════════════════════════════════════════════════');
  console.log('🌍 Transform World - Knowledge Sharing Platform');
  console.log('═══════════════════════════════════════════════════════════');
  console.log('');
  console.log(`✅ Server is running on http://${HOST}:${PORT}`);
  console.log('');
  console.log('📱 Access the platform:');
  console.log(`   Web Interface: http://localhost:${PORT}/`);
  console.log(`   API Endpoint:  http://localhost:${PORT}/api/articles`);
  console.log('');
  console.log('💡 This platform enables:');
  console.log('   • Knowledge sharing across borders');
  console.log('   • Collaborative learning');
  console.log('   • Democratized education');
  console.log('   • Community-driven content');
  console.log('');
  console.log('🚀 Press Ctrl+C to stop the server');
  console.log('═══════════════════════════════════════════════════════════');
  console.log('');
});

// Handle graceful shutdown
process.on('SIGTERM', () => {
  console.log('\n\n👋 Shutting down gracefully...');
  server.close(() => {
    console.log('✅ Server closed');
    process.exit(0);
  });
});

process.on('SIGINT', () => {
  console.log('\n\n👋 Shutting down gracefully...');
  server.close(() => {
    console.log('✅ Server closed');
    process.exit(0);
  });
});
