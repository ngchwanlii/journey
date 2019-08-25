//
//  AsrManager.m
//  Runner
//
//  Created by Jay Ng on 8/22/19.
//  Copyright © 2019 The Chromium Authors. All rights reserved.
//

#import "AsrManager.h"
#import "BDSEventManager.h"
#import "BDSASRDefines.h"
#import "BDSASRParameters.h"

const NSString* API_KEY = @"yRN29bqTQQkg9ovpkdwwDzb2";
const NSString* SECRET_KEY = @"pMSC79DXdOsx0URna4O74TPabveC5jNG";
const NSString* APP_ID = @"17067817";

@interface AsrManager ()  <BDSClientASRDelegate>
@property (strong, nonatomic) BDSEventManager *asrEventManager;
@property (nonatomic, copy) AsrCallback asrSuccess;
@property (nonatomic, copy) AsrCallback asrFailure;
@end

@implementation AsrManager
+(instancetype)initWith:(AsrCallback)success failure:
(AsrCallback)failure {
    AsrManager *asrManager = [AsrManager new];
    asrManager.asrFailure = failure;
    asrManager.asrSuccess = success;
    return asrManager;
}

-(id)init {
    self = [super init];
    self.asrEventManager = [BDSEventManager
                            createEventManagerWithName:BDS_ASR_NAME];
    [self configVoiceRecognitionClient];
    return self;
}

#pragma mark - Action
-(void) start {
    [self.asrEventManager setParameter:@(NO)
                                forKey:BDS_ASR_NEED_CACHE_AUDIO];
    [self.asrEventManager setDelegate:self];
    [self.asrEventManager setParameter:nil
                                forKey:BDS_ASR_AUDIO_FILE_PATH];
    [self.asrEventManager setParameter:@(YES) forKey:BDS_ASR_AUDIO_INPUT_STREAM];
    [self.asrEventManager sendCommand:BDS_ASR_CMD_START];
}

-(void) stop {
    [self.asrEventManager sendCommand:BDS_ASR_CMD_STOP];
}

-(void) cancel {
    [self.asrEventManager sendCommand:BDS_ASR_CMD_CANCEL];
}


#pragma mark - Private: Configuration

- (void)configVoiceRecognitionClient {
    
    [self.asrEventManager setParameter:@(EVRDebugLogLevelTrace) forKey:BDS_ASR_DEBUG_LOG_LEVEL];

    [self.asrEventManager setParameter:@[API_KEY, SECRET_KEY] forKey:BDS_ASR_API_SECRET_KEYS];
    [self.asrEventManager setParameter:APP_ID forKey:BDS_ASR_OFFLINE_APP_CODE];

    [self configModelVAD];
    //    [self configDNNMFE];
    
    //    [self.asrEventManager setParameter:@"15361" forKey:BDS_ASR_PRODUCT_ID];
    //    [self enableNLU];
    //    [self enablePunctuation];
    // ------------------------
        
    //    [self configSmartAsr];
}


- (void) enableNLU {
    [self.asrEventManager setParameter:@(YES) forKey:BDS_ASR_ENABLE_NLU];
    [self.asrEventManager setParameter:@"15361" forKey:BDS_ASR_PRODUCT_ID];
}

- (void) enablePunctuation {
    [self.asrEventManager setParameter:@(NO) forKey:BDS_ASR_DISABLE_PUNCTUATION];
    // chinese punctuation
    //    [self.asrEventManager setParameter:@"1537" forKey:BDS_ASR_PRODUCT_ID];
    // english punctuation
    [self.asrEventManager setParameter:@"1737" forKey:BDS_ASR_PRODUCT_ID];
    
}


- (void)configModelVAD {
    NSString *modelVAD_filepath = [[NSBundle mainBundle] pathForResource:@"bds_easr_basic_model" ofType:@"dat"];
    [self.asrEventManager setParameter:modelVAD_filepath forKey:BDS_ASR_MODEL_VAD_DAT_FILE];
    [self.asrEventManager setParameter:@(YES) forKey:BDS_ASR_ENABLE_MODEL_VAD];
}

#pragma mark - MVoiceRecognitionClientDelegate

- (void)VoiceRecognitionClientWorkStatus:(int)workStatus obj:(id)aObj {
    switch (workStatus) {
        case EVoiceRecognitionClientWorkStatusNewRecordData: {
            break;
        }
            
        case EVoiceRecognitionClientWorkStatusStartWorkIng: {
            break;
        }
        case EVoiceRecognitionClientWorkStatusStart: {
            break;
        }
        case EVoiceRecognitionClientWorkStatusEnd: {
            break;
        }
        case EVoiceRecognitionClientWorkStatusFlushData: {
            break;
        }
        case EVoiceRecognitionClientWorkStatusFinish: { // voice recognization functions completed
            // return result
            if ([aObj isKindOfClass:[NSDictionary class]]) {
                NSString *result = aObj[@"results_recognition"][0];
                if (self.asrSuccess) {
                    self.asrSuccess(result);
                }
            }
            break;
        }
        case EVoiceRecognitionClientWorkStatusMeterLevel: {
            break;
        }
        case EVoiceRecognitionClientWorkStatusCancel: {
            //
            break;
        }
        case EVoiceRecognitionClientWorkStatusError: { // when error happened
            if (self.asrFailure) {
                self.asrFailure([((NSError *) aObj) description]);
            }
            break;
        }
        case EVoiceRecognitionClientWorkStatusLoaded: {
            break;
        }
        case EVoiceRecognitionClientWorkStatusUnLoaded: {
            break;
        }
        case EVoiceRecognitionClientWorkStatusChunkThirdData: {
            break;
        }
        case EVoiceRecognitionClientWorkStatusChunkNlu: {
            break;
        }
        case EVoiceRecognitionClientWorkStatusChunkEnd: {
            break;
        }
        case EVoiceRecognitionClientWorkStatusFeedback: {
            break;
        }
        case EVoiceRecognitionClientWorkStatusRecorderEnd: {
            break;
        }
        case EVoiceRecognitionClientWorkStatusLongSpeechEnd: {
            break;
        }
        default:
            break;
    }
}


@end
