define({ "api": [
  {
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "optional": false,
            "field": "varname1",
            "description": "<p>No type.</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "varname2",
            "description": "<p>With type.</p>"
          }
        ]
      }
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "src/main/resources/apidoc/main.js",
    "group": "D__workspace_agoravideo_back_src_main_resources_apidoc_main_js",
    "groupTitle": "D__workspace_agoravideo_back_src_main_resources_apidoc_main_js",
    "name": ""
  },
  {
    "type": "post",
    "url": "/check/alive",
    "title": "3、图像活体检测",
    "description": "<p>图像活体检测 (人脸静态活体检测) 0,1</p>",
    "group": "group000_Intelligent_voice",
    "name": "checkAlive",
    "version": "1.0.0",
    "sampleRequest": [
      {
        "url": "http://172.30.1.65:8088/agora/check/alive"
      }
    ],
    "permission": [
      {
        "name": "admin"
      }
    ],
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>访问token</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "imgUrl",
            "description": "<p>图片地址信息</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "channelNo",
            "description": "<p>通道信息</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "成功响应": [
          {
            "group": "成功响应",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>请求返回码 0:成功,其它请参见文档定义</p>"
          },
          {
            "group": "成功响应",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>请求返回信息</p>"
          },
          {
            "group": "成功响应",
            "type": "Json",
            "optional": false,
            "field": "data",
            "description": "<p>成功后返回参数</p>"
          },
          {
            "group": "成功响应",
            "type": "Json",
            "optional": false,
            "field": "data.flag",
            "description": "<p>匹配结果信息 ，此接口暂时未用到此字段信息</p>"
          },
          {
            "group": "成功响应",
            "type": "Json",
            "optional": false,
            "field": "data.resultDetails",
            "description": "<p>语音地址信息</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功响应示例:",
          "content": "<p>\n{\n\"code\": \"0\" ,\n\"msg\": \"success\",\n\"data\": {\n}\n}",
          "type": "Json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败响应示例:",
          "content": "{\n\"code\": \"-1\",\n\"msg\": \"exception\",\n\"data\": \"\"\n}",
          "type": "Json"
        }
      ]
    },
    "filename": "src/main/java/com/gwghk/agora/video/agoravideo/controller/DemoController.java",
    "groupTitle": "group000_Intelligent_voice"
  },
  {
    "type": "post",
    "url": "/check/identity",
    "title": "2、图像人身核实",
    "description": "<p>图像人身核实 (用户上传照片身份信息核验) 0,1及相似度</p>",
    "group": "group000_Intelligent_voice",
    "name": "checkIdentity",
    "version": "1.0.0",
    "sampleRequest": [
      {
        "url": "http://172.30.1.65:8088/agora/check/identity"
      }
    ],
    "permission": [
      {
        "name": "admin"
      }
    ],
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>访问token</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "channelNo",
            "description": "<p>通道信息</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "idcardNumber",
            "description": "<p>证件号</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "idcardName",
            "description": "<p>证件名称</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "imgUrl",
            "description": "<p>图片地址信息</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "成功响应": [
          {
            "group": "成功响应",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>请求返回码 0:成功,其它请参见文档定义</p>"
          },
          {
            "group": "成功响应",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>请求返回信息</p>"
          },
          {
            "group": "成功响应",
            "type": "Json",
            "optional": false,
            "field": "data",
            "description": "<p>成功后返回参数</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功响应示例:",
          "content": "<p>\n{\n\"code\": \"0\" ,\n\"msg\": \"success\",\n\"data\": {\n}\n}",
          "type": "Json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败响应示例:",
          "content": "{\n\"code\": \"-1\",\n\"msg\": \"exception\",\n\"data\": \"\"\n}",
          "type": "Json"
        }
      ]
    },
    "filename": "src/main/java/com/gwghk/agora/video/agoravideo/controller/DemoController.java",
    "groupTitle": "group000_Intelligent_voice"
  },
  {
    "type": "post",
    "url": "/result/get",
    "title": "6、根据通道获取结果信息",
    "description": "<p>获取结果信息</p>",
    "group": "group000_Intelligent_voice",
    "name": "getResult",
    "version": "1.0.0",
    "sampleRequest": [
      {
        "url": "http://172.30.1.65:8088/agora/result/get"
      }
    ],
    "permission": [
      {
        "name": "admin"
      }
    ],
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>访问token</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "channelNo",
            "description": "<p>通道信息</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "成功响应": [
          {
            "group": "成功响应",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>请求返回码 0:成功,其它请参见文档定义</p>"
          },
          {
            "group": "成功响应",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>请求返回信息</p>"
          },
          {
            "group": "成功响应",
            "type": "Json",
            "optional": false,
            "field": "data",
            "description": "<p>成功后返回参数</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功响应示例:",
          "content": "<p>\n{\n\"code\": \"0\" ,\n\"msg\": \"success\",\n\"data\": {\n\n }\n}",
          "type": "Json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败响应示例:",
          "content": "{\n\"code\": \"-1\",\n\"msg\": \"exception\",\n\"data\": \"\"\n}",
          "type": "Json"
        }
      ]
    },
    "filename": "src/main/java/com/gwghk/agora/video/agoravideo/controller/ResultController.java",
    "groupTitle": "group000_Intelligent_voice"
  },
  {
    "type": "post",
    "url": "/voice/asr",
    "title": "4、语音识别接口",
    "description": "<p>语音识别接口</p>",
    "group": "group000_Intelligent_voice",
    "name": "identity",
    "version": "1.0.0",
    "sampleRequest": [
      {
        "url": "http://172.30.1.65:8088/agora/voice/asr"
      }
    ],
    "permission": [
      {
        "name": "admin"
      }
    ],
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>访问token</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "url",
            "description": "<p>音频地址信息</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "voiceFormat",
            "description": "<p>音频格式信息</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "channelNo",
            "description": "<p>通道信息</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "成功响应": [
          {
            "group": "成功响应",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>请求返回码 0:成功,其它请参见文档定义</p>"
          },
          {
            "group": "成功响应",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>请求返回信息</p>"
          },
          {
            "group": "成功响应",
            "type": "Json",
            "optional": false,
            "field": "data",
            "description": "<p>成功后返回参数</p>"
          },
          {
            "group": "成功响应",
            "type": "Json",
            "optional": false,
            "field": "data.flag",
            "description": "<p>匹配结果信息 -1:检测不到或各异常；0:与期望结果不一致，1:与期望结果一致</p>"
          },
          {
            "group": "成功响应",
            "type": "Json",
            "optional": false,
            "field": "data.resultDetails",
            "description": "<p>原始语音信息</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功响应示例:",
          "content": "<p>\n{\n\"code\": \"0\" ,\n\"msg\": \"success\",\n\"data\": {\n     \"V_ASR\": {\n            \"flag\": 0,\n            \"otherData\": null,\n            \"resultDetails\": \"请问你叫什么名字。\"\n        }\n}\n}",
          "type": "Json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败响应示例:",
          "content": "{\n\"code\": \"-1\",\n\"msg\": \"exception\",\n\"data\": \"\"\n}",
          "type": "Json"
        }
      ]
    },
    "filename": "src/main/java/com/gwghk/agora/video/agoravideo/controller/IntelligentController.java",
    "groupTitle": "group000_Intelligent_voice"
  },
  {
    "type": "post",
    "url": "/ocr/idCard",
    "title": "1、身份证ocr识别",
    "description": "<p>身份证ocr识别</p>",
    "group": "group000_Intelligent_voice",
    "name": "ocrIdCard",
    "version": "1.0.0",
    "sampleRequest": [
      {
        "url": "http://172.30.1.65:8088/agora/ocr/idCard"
      }
    ],
    "permission": [
      {
        "name": "admin"
      }
    ],
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>访问token</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "channelNo",
            "description": "<p>通道信息</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "imgUrl",
            "description": "<p>图片地址信息</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "cardType",
            "description": "<p>0 为识别身份证有照片的一面，1 为识别身份证有国徽的一面；如果未指定，默认为0。</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "成功响应": [
          {
            "group": "成功响应",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>请求返回码 0:成功,其它请参见文档定义</p>"
          },
          {
            "group": "成功响应",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>请求返回信息</p>"
          },
          {
            "group": "成功响应",
            "type": "Json",
            "optional": false,
            "field": "data",
            "description": "<p>成功后返回参数</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功响应示例:",
          "content": "<p>\n{\n\"code\": \"0\" ,\n\"msg\": \"success\",\n\"data\": {\n}\n}",
          "type": "Json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败响应示例:",
          "content": "{\n\"code\": \"-1\",\n\"msg\": \"exception\",\n\"data\": \"\"\n}",
          "type": "Json"
        }
      ]
    },
    "filename": "src/main/java/com/gwghk/agora/video/agoravideo/controller/DemoController.java",
    "groupTitle": "group000_Intelligent_voice"
  },
  {
    "type": "post",
    "url": "/voice/tts",
    "title": "5、语音合成接口",
    "description": "<p>语音合成接口</p>",
    "group": "group000_Intelligent_voice",
    "name": "textToVoice",
    "version": "1.0.0",
    "sampleRequest": [
      {
        "url": "http://172.30.1.65:8088/agora/voice/tts"
      }
    ],
    "permission": [
      {
        "name": "admin"
      }
    ],
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>访问token</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "text",
            "description": "<p>合成语音文字信息</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "channelNo",
            "description": "<p>通道信息</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "成功响应": [
          {
            "group": "成功响应",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>请求返回码 0:成功,其它请参见文档定义</p>"
          },
          {
            "group": "成功响应",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>请求返回信息</p>"
          },
          {
            "group": "成功响应",
            "type": "Json",
            "optional": false,
            "field": "data",
            "description": "<p>成功后返回参数</p>"
          },
          {
            "group": "成功响应",
            "type": "Json",
            "optional": false,
            "field": "data.flag",
            "description": "<p>匹配结果信息 ，此接口暂时未用到此字段信息</p>"
          },
          {
            "group": "成功响应",
            "type": "Json",
            "optional": false,
            "field": "data.resultDetails",
            "description": "<p>语音地址信息</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功响应示例:",
          "content": "<p>\n{\n\"code\": \"0\" ,\n\"msg\": \"success\",\n\"data\": {\n     \"V_asr\": {\n            \"flag\": 0,\n            \"otherData\": null,\n            \"resultDetails\": \"http://ip:port/xx/1.wav\"\n        }\n}\n}",
          "type": "Json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败响应示例:",
          "content": "{\n\"code\": \"-1\",\n\"msg\": \"exception\",\n\"data\": \"\"\n}",
          "type": "Json"
        }
      ]
    },
    "filename": "src/main/java/com/gwghk/agora/video/agoravideo/controller/IntelligentController.java",
    "groupTitle": "group000_Intelligent_voice"
  },
  {
    "type": "post",
    "url": "/common/uploadImage",
    "title": "1、文件上传",
    "description": "<p>文件上传</p>",
    "group": "group000_common",
    "name": "uploadFile",
    "version": "1.0.0",
    "sampleRequest": [
      {
        "url": "http://172.30.1.65:8088/agora/common/uploadImage"
      }
    ],
    "permission": [
      {
        "name": "admin"
      }
    ],
    "header": {
      "fields": {
        "Header": [
          {
            "group": "Header",
            "type": "String",
            "optional": false,
            "field": "Authorization",
            "description": "<p>访问token</p>"
          }
        ]
      }
    },
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "int",
            "optional": false,
            "field": "file",
            "description": "<p>文件域参数名称</p>"
          }
        ]
      }
    },
    "success": {
      "fields": {
        "成功响应": [
          {
            "group": "成功响应",
            "type": "String",
            "optional": false,
            "field": "code",
            "description": "<p>请求返回码 0:成功,其它请参见文档定义</p>"
          },
          {
            "group": "成功响应",
            "type": "String",
            "optional": false,
            "field": "msg",
            "description": "<p>请求返回信息</p>"
          },
          {
            "group": "成功响应",
            "type": "Json",
            "optional": false,
            "field": "data",
            "description": "<p>上传成功后返回参数</p>"
          },
          {
            "group": "成功响应",
            "type": "String",
            "optional": false,
            "field": "data.accessUrl",
            "description": "<p>上传成功后文件访问路径</p>"
          },
          {
            "group": "成功响应",
            "type": "String",
            "optional": false,
            "field": "data.storageName",
            "description": "<p>上传成功后服务器存储的文件名</p>"
          },
          {
            "group": "成功响应",
            "type": "String",
            "optional": false,
            "field": "data.fileName",
            "description": "<p>上传的文件原文件名</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "成功响应示例:",
          "content": "<p>\n{\n\"code\": \"0000\" ,\n\"msg\": \"请求成功\",\n\"data\": {\n\"fileName\": \"selfedit_one.apk\",\n\"accessUrl\": \"http://172.27.1.104:82/ea208b14-3487-4937-81ba-e8c1ff313220.apk\",\n\"storageName\": \"ea208b14-3487-4937-81ba-e8c1ff313220.apk\"\n}\n}",
          "type": "Json"
        }
      ]
    },
    "error": {
      "examples": [
        {
          "title": "失败响应示例:",
          "content": "{\n\"code\": \"9999\",\n\"msg\": \"请求服务器异常\",\n\"data\": \"\"\n}",
          "type": "Json"
        }
      ]
    },
    "filename": "src/main/java/com/gwghk/agora/video/agoravideo/controller/FileController.java",
    "groupTitle": "group000_common"
  }
] });
