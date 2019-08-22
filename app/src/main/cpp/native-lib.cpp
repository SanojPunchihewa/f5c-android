#include <jni.h>
#include <string>
#include "interface.h"
#include <zlib.h>
#include <stdio.h>
#include<iostream>

#include <android/log.h>

#define  LOG_TAG    "f5c"

#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
// If you want you can add other log definition for info, warning etc


extern "C" JNIEXPORT jstring JNICALL
Java_com_example_hello_1f5c_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}


extern "C" JNIEXPORT jint JNICALL
Java_com_example_hello_1f5c_MainActivity_init(JNIEnv* env, jclass clazz,jstring command) {
    // try{TODO:exceptions
    // Convert command to cpp
    //TODO:casting not good
    char *command_c = (char*)env->GetStringUTFChars(command, nullptr);
    // if(!command_c) {throwJavaError(env, "jvm could not allocate memory");return;};
    // std::string command_s = command_c;


    enum { kMaxArgs = 64 };
    int argc = 0;
    char *argv[kMaxArgs];

    // char commandLine[200] = "f5c index -d ../../test/ecoli_2kb_region/fast5_files/ ../../test/ecoli_2kb_region/reads.fasta";
    // char commandLine[200] = "f5c call-methylation -b ../../test/ecoli_2kb_region/reads.sorted.bam -g ../../test/ecoli_2kb_region/draft.fa -r ../../test/ecoli_2kb_region/reads.fasta --secondary=yes --min-mapq=0 -B 2M > ../../test/ecoli_2kb_region/result.txt";
    // char commandLine[500] = "f5c eventalign -b ../../test/ecoli_2kb_region/reads.sorted.bam -g ../../test/ecoli_2kb_region/draft.fa -r ../../test/ecoli_2kb_region/reads.fasta --secondary=yes --min-mapq=0 -B 2M > ../../test/ecoli_2kb_region/f5c_event_align.txt";
    // char commandLine[200] = "f5c meth-freq -d ../../test/ecoli_2kb_region/fast5_files/ ../../test/ecoli_2kb_region/reads.fasta";
    LOGD("command %s",command_c);
    char *p2 = strtok(command_c, " ");

    while (p2 && argc < kMaxArgs-1)
    {
        argv[argc++] = p2;
        p2 = strtok(0, " ");
    }
    argv[argc] = 0;
    jint result = init(argc,argv);

    env->ReleaseStringUTFChars(command,command_c);

    return result;
//    return 100;
    // } CATCH_AND_RETHROW;
}